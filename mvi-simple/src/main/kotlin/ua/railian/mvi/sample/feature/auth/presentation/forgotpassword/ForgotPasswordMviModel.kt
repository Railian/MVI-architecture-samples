package ua.railian.mvi.sample.feature.auth.presentation.forgotpassword

import kotlinx.coroutines.flow.update
import ua.railian.architecture.mvi.simple.AbstractMviModelWithActions
import ua.railian.data.result.onFailure
import ua.railian.data.result.onSuccess
import ua.railian.mvi.sample.feature.auth.domain.error.ForgotPasswordError
import ua.railian.mvi.sample.feature.auth.domain.usecase.ForgotPassword
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordMviModel.Action
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordMviModel.Intent
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordMviModel.State

class ForgotPasswordMviModel(
    initialState: State,
    private val forgotPassword: ForgotPassword,
) : AbstractMviModelWithActions<State, Intent, Action>(
    initialState = initialState,
) {
    data class State(
        val email: String,
        val loading: Boolean = false,
        val emailError: ForgotPasswordError.Email? = null,
    )

    sealed interface Intent {
        data class ChangeEmail(val email: String) : Intent
        data object SendInstructions : Intent
    }

    sealed interface Action {
        data object InstructionsSent : Action
    }

    override suspend fun PipelineScope.process(intent: Intent) {
        when (intent) {
            is Intent.ChangeEmail -> changeEmail(intent)
            is Intent.SendInstructions -> sendInstructions()
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

    private suspend fun PipelineScope.sendInstructions() {
        state.update { it.copy(loading = true) }
        forgotPassword(email = state.value.email)
            .onSuccess { actions.send(Action.InstructionsSent) }
            .onFailure { error ->
                state.update {
                    val emailError = error as? ForgotPasswordError.Email
                    it.copy(emailError = emailError)
                }
            }
        state.update { it.copy(loading = false) }
    }
}
