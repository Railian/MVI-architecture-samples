package ua.railian.mvi.sample.feature.auth.presentation.login

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import ua.railian.architecture.mvi.simple.AbstractMviModelWithActions
import ua.railian.data.result.onFailure
import ua.railian.data.result.onSuccess
import ua.railian.mvi.sample.feature.auth.domain.error.LoginError
import ua.railian.mvi.sample.feature.auth.domain.usecase.GetLastLoggedInUserEmail
import ua.railian.mvi.sample.feature.auth.domain.usecase.LoginWithEmail
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginMviModel.Action
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginMviModel.Intent
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginMviModel.State

class LoginMviModel(
    initialState: State,
    private val getLastLoggedInUserEmail: GetLastLoggedInUserEmail,
    private val loginWithEmail: LoginWithEmail,
) : AbstractMviModelWithActions<State, Intent, Action>(
    initialState = initialState,
    initialIntents = flowOf(Intent.Init),
) {
    data class State(
        val email: String,
        val password: String,
        val loading: Boolean = false,
        val emailError: LoginError.Email? = null,
        val passwordError: LoginError.Password? = null,
    )

    sealed interface Intent {
        data object Init : Intent
        data class ChangeEmail(val email: String) : Intent
        data class ChangePassword(val password: String) : Intent
        data object Login : Intent
    }

    sealed interface Action {
        data object LoggedIn : Action
        data class Error(val error: LoginError.Common) : Action
    }

    override suspend fun PipelineScope.process(intent: Intent) {
        when (intent) {
            is Intent.Init -> init()
            is Intent.ChangeEmail -> changeEmail(intent)
            is Intent.ChangePassword -> changePassword(intent)
            is Intent.Login -> login()
        }
    }

    private suspend fun PipelineScope.init() {
        state.update {
            val lastEmail = getLastLoggedInUserEmail()
            it.copy(email = lastEmail.orEmpty())
        }
    }

    private fun PipelineScope.changeEmail(
        intent: Intent.ChangeEmail,
    ) {
        state.update {
            it.copy(
                email = intent.email,
                emailError = null,
            )
        }
    }

    private fun PipelineScope.changePassword(
        intent: Intent.ChangePassword,
    ) {
        state.update {
            it.copy(
                password = intent.password,
                passwordError = null,
            )
        }
    }

    private suspend fun PipelineScope.login() {
        state.update { it.copy(loading = true) }
        loginWithEmail(
            email = state.value.email,
            password = state.value.password,
        ).onSuccess {
            actions.send(Action.LoggedIn)
        }.onFailure { errors ->
            state.update {
                it.copy(
                    emailError = errors.filterIsInstance<LoginError.Email>().firstOrNull(),
                    passwordError = errors.filterIsInstance<LoginError.Password>().firstOrNull(),
                )
            }
            errors.filterIsInstance<LoginError.Common>().forEach { error ->
                actions.send(Action.Error(error))
            }
        }
        state.update { it.copy(loading = false) }
    }
}
