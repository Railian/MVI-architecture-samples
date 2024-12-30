package ua.railian.mvi.sample.feature.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordScreen
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginScreen
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpScreen
import ua.railian.mvi.sample.feature.home.HomeRoute

@Serializable
data object AuthRoute {

    @Serializable
    data object Login

    @Serializable
    data class ForgotPassword(
        val email: String = "",
    )

    @Serializable
    data class SignUp(
        val email: String = "",
        val password: String = "",
    )
}

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
) {
    navigation<AuthRoute>(
        startDestination = AuthRoute.Login,
    ) {
        composable<AuthRoute.Login> {
            LoginScreen(
                onNavigateToForgotPassword = { email ->
                    navController.navigate(AuthRoute.ForgotPassword(email))
                },
                onNavigateToSignUp = { email, password ->
                    navController.navigate(AuthRoute.SignUp(email, password))
                },
                onLoggedIn = {
                    navController.navigate(HomeRoute) {
                        popUpTo(AuthRoute) { inclusive = true }
                    }
                },
            )
        }
        composable<AuthRoute.ForgotPassword> {
            ForgotPasswordScreen(
                onNavigateBack = navController::popBackStack,
                mviModel = koinViewModel {
                    parametersOf(it.toRoute<AuthRoute.ForgotPassword>())
                }
            )
        }
        composable<AuthRoute.SignUp> {
            SignUpScreen(
                onNavigateBack = navController::popBackStack,
                onSignedUp = {
                    navController.navigate(HomeRoute) {
                        popUpTo(AuthRoute) { inclusive = true }
                    }
                },
                mviModel = koinViewModel {
                    parametersOf(it.toRoute<AuthRoute.SignUp>())
                },
            )
        }
    }
}
