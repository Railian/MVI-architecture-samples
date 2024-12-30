package ua.railian.mvi.sample.feature.home.presentation.dashboard

import kotlinx.coroutines.flow.update
import ua.railian.architecture.mvi.simple.AbstractMviModelWithActions
import ua.railian.data.result.onFailure
import ua.railian.data.result.onSuccess
import ua.railian.mvi.sample.feature.auth.domain.usecase.Logout
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel.Action
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel.Intent
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel.State

class DashboardMviModel(
    initialState: State,
    private val logout: Logout,
) : AbstractMviModelWithActions<State, Intent, Action>(
    initialState = initialState,
) {
    data class State(
        val loading: Boolean = false,
    )

    sealed interface Intent {
        data object Logout : Intent
    }

    sealed interface Action {
        data object LoggedOut : Action
        data class Error(val message: String) : Action
    }

    override suspend fun PipelineScope.process(intent: Intent) {
        when (intent) {
            is Intent.Logout -> {
                state.update { it.copy(loading = true) }
                logout()
                    .onSuccess { actions.send(Action.LoggedOut) }
                    .onFailure { actions.send(Action.Error(it)) }
                state.update { it.copy(loading = false) }
            }
        }
    }
}
