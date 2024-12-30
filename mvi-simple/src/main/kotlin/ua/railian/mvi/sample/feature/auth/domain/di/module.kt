package ua.railian.mvi.sample.feature.auth.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ua.railian.mvi.sample.feature.auth.domain.usecase.ForgotPassword
import ua.railian.mvi.sample.feature.auth.domain.usecase.ForgotPasswordUseCase
import ua.railian.mvi.sample.feature.auth.domain.usecase.GetLastLoggedInUserEmail
import ua.railian.mvi.sample.feature.auth.domain.usecase.GetLastLoggedInUserEmailUseCase
import ua.railian.mvi.sample.feature.auth.domain.usecase.IsUserLoggedIn
import ua.railian.mvi.sample.feature.auth.domain.usecase.IsUserLoggedInUseCase
import ua.railian.mvi.sample.feature.auth.domain.usecase.LoginWithEmail
import ua.railian.mvi.sample.feature.auth.domain.usecase.LoginWithEmailUseCase
import ua.railian.mvi.sample.feature.auth.domain.usecase.Logout
import ua.railian.mvi.sample.feature.auth.domain.usecase.LogoutUseCase
import ua.railian.mvi.sample.feature.auth.domain.usecase.SignUp
import ua.railian.mvi.sample.feature.auth.domain.usecase.SignUpUseCase
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidator
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidatorImpl
import ua.railian.mvi.sample.feature.auth.domain.validator.PasswordValidator
import ua.railian.mvi.sample.feature.auth.domain.validator.PasswordValidatorImpl

private val authUseCaseModule = module {
    factoryOf(::IsUserLoggedInUseCase) bind IsUserLoggedIn::class
    factoryOf(::GetLastLoggedInUserEmailUseCase) bind GetLastLoggedInUserEmail::class
    factoryOf(::LoginWithEmailUseCase) bind LoginWithEmail::class
    factoryOf(::LogoutUseCase) bind Logout::class
    factoryOf(::SignUpUseCase) bind SignUp::class
    factoryOf(::ForgotPasswordUseCase) bind ForgotPassword::class
}

private val authValidatorModule = module {
    factoryOf(::EmailValidatorImpl) bind EmailValidator::class
    factoryOf(::PasswordValidatorImpl) bind PasswordValidator::class
}

val authDomainModule = module {
    includes(
        authUseCaseModule,
        authValidatorModule,
    )
}
