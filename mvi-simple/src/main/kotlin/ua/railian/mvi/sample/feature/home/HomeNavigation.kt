package ua.railian.mvi.sample.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import ua.railian.mvi.sample.feature.auth.AuthRoute
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardScreen

@Serializable
data object HomeRoute {

    @Serializable
    data object Dashboard
}

fun NavGraphBuilder.homeNavigation(
    navController: NavHostController,
) {
    navigation<HomeRoute>(
        startDestination = HomeRoute.Dashboard,
    ) {
        composable<HomeRoute.Dashboard> {
            DashboardScreen(
                onLoggedOut = {
                    navController.navigate(AuthRoute) {
                        popUpTo(HomeRoute) { inclusive = true }
                    }
                },
            )
        }
    }
}
