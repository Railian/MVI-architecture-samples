package ua.railian.mvi.sample.feature.home.di

import org.koin.dsl.module
import ua.railian.mvi.sample.feature.home.presentation.di.homePresentationModule

val homeFeatureModule = module {
    includes(
        homePresentationModule,
    )
}
