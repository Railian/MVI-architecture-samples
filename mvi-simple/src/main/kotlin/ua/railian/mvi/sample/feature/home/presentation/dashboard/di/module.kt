package ua.railian.mvi.sample.feature.home.presentation.dashboard.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel.State

internal val dashboardScreenModule = module {
    viewModel {
        DashboardMviModel(
            initialState = State(
                loading = false,
            ),
            logout = get(),
        )
    }
}
