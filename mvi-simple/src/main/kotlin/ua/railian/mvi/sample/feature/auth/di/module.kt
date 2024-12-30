package ua.railian.mvi.sample.feature.auth.di

import org.koin.dsl.module
import ua.railian.mvi.sample.feature.auth.data.di.authDataModule
import ua.railian.mvi.sample.feature.auth.domain.di.authDomainModule
import ua.railian.mvi.sample.feature.auth.presentation.di.authPresentationModule

val authFeatureModule = module {
    includes(
        authDataModule,
        authDomainModule,
        authPresentationModule,
    )
}
