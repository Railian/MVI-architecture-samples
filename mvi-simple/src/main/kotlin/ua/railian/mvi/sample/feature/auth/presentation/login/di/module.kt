package ua.railian.mvi.sample.feature.auth.presentation.login.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginMviModel

val loginScreenModule = module {
    viewModel {
        LoginMviModel(
            initialState = LoginMviModel.State(
                email = "",
                password = "",
            ),
            getLastLoggedInUserEmail = get(),
            loginWithEmail = get(),
        )
    }
}
