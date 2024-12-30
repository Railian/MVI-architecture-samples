package ua.railian.mvi.sample.feature.home.presentation.di

import org.koin.dsl.module
import ua.railian.mvi.sample.feature.home.presentation.dashboard.di.dashboardScreenModule

val homePresentationModule = module {
    includes(
        dashboardScreenModule,
    )
}
