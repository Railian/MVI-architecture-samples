package ua.railian.mvi.sample.feature.auth.data.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ua.railian.mvi.sample.feature.auth.data.repository.AuthRepositoryMock
import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository

val authDataModule = module {
    singleOf(::AuthRepositoryMock) bind AuthRepository::class
}
