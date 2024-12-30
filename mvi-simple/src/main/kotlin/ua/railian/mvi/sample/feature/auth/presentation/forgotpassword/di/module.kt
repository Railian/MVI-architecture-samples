package ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ua.railian.mvi.sample.feature.auth.AuthRoute
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordMviModel

internal val forgotPasswordScreenModule = module {
    viewModel {
        val params = it.getOrNull<AuthRoute.ForgotPassword>()
        ForgotPasswordMviModel(
            initialState = ForgotPasswordMviModel.State(
                email = params?.email.orEmpty(),
            ),
            forgotPassword = get(),
        )
    }
}
