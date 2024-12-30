package ua.railian.mvi.sample.feature.auth.presentation.di

import org.koin.dsl.module
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.di.forgotPasswordScreenModule
import ua.railian.mvi.sample.feature.auth.presentation.login.di.loginScreenModule
import ua.railian.mvi.sample.feature.auth.presentation.signup.di.signUpScreenModule

val authPresentationModule = module {
    includes(
        loginScreenModule,
        signUpScreenModule,
        forgotPasswordScreenModule,
    )
}
