package ua.railian.mvi.sample.feature.auth.presentation.signup.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.railian.mvi.sample.feature.auth.AuthRoute
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpMviModel

internal val signUpScreenModule = module {
    viewModel {
        val params = it.getOrNull<AuthRoute.SignUp>()
        SignUpMviModel(
            initialState = SignUpMviModel.State(
                email = params?.email.orEmpty(),
                password = params?.password.orEmpty(),
                passwordConfirmation = "",
            ),
            signUp = get(),
        )
    }
}
