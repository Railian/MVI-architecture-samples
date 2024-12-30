package ua.railian.mvi.sample.feature.auth.presentation.signup

import kotlinx.coroutines.flow.update
import ua.railian.architecture.mvi.simple.AbstractMviModelWithActions
import ua.railian.data.result.onFailure
import ua.railian.data.result.onSuccess
import ua.railian.mvi.sample.feature.auth.domain.error.SignUpError
import ua.railian.mvi.sample.feature.auth.domain.usecase.SignUp
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpMviModel.Action
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpMviModel.Intent
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpMviModel.State

class SignUpMviModel(
    initialState: State,
    private val signUp: SignUp,
) : AbstractMviModelWithActions<State, Intent, Action>(
    initialState = initialState,
) {
    data class State(
        val email: String,
        val password: String,
        val passwordConfirmation: String,
        val loading: Boolean = false,
        val emailError: SignUpError.Email? = null,
        val passwordError: SignUpError.Password? = null,
        val passwordConfirmationError: SignUpError.PasswordConfirmation? = null,
    )

    sealed interface Intent {
        data class ChangeEmail(val email: String) : Intent
        data class ChangePassword(val password: String) : Intent
        data class ChangePasswordConfirmation(val password: String) : Intent
        data object SignUp : Intent
    }

    sealed interface Action {
        data object SignedUp : Action
        data class Error(val error: SignUpError) : Action
    }

    override suspend fun PipelineScope.process(intent: Intent) {
        when (intent) {
            is Intent.ChangeEmail -> changeEmail(intent)
            is Intent.ChangePassword -> changePassword(intent)
            is Intent.ChangePasswordConfirmation -> changePasswordConfirmation(intent)
            is Intent.SignUp -> signUp()
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
                passwordConfirmationError = null,
            )
        }
    }

    private fun PipelineScope.changePasswordConfirmation(
        intent: Intent.ChangePasswordConfirmation,
    ) {
        state.update {
            it.copy(
                passwordConfirmation = intent.password,
                passwordConfirmationError = null,
            )
        }
    }

    private suspend fun PipelineScope.signUp() {
        state.update { it.copy(loading = true) }
        signUp(
            email = state.value.email,
            password = state.value.password,
            passwordConfirmation = state.value.passwordConfirmation,
        ).onSuccess {
            actions.send(Action.SignedUp)
        }.onFailure { errors ->
            state.update {
                it.copy(
                    emailError = errors.filterIsInstance<SignUpError.Email>().firstOrNull(),
                    passwordError = errors.filterIsInstance<SignUpError.Password>().firstOrNull(),
                    passwordConfirmationError = errors.filterIsInstance<SignUpError.PasswordConfirmation>()
                        .firstOrNull(),
                )
            }
            errors.filterIsInstance<SignUpError.Common>().forEach { error ->
                actions.send(Action.Error(error))
            }
        }
        state.update { it.copy(loading = false) }
    }
}
