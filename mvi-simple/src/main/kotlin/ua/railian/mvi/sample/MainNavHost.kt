package ua.railian.mvi.sample

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import ua.railian.mvi.sample.feature.auth.AuthRoute
import ua.railian.mvi.sample.feature.auth.authNavigation
import ua.railian.mvi.sample.feature.auth.domain.usecase.IsUserLoggedIn
import ua.railian.mvi.sample.feature.home.HomeRoute
import ua.railian.mvi.sample.feature.home.homeNavigation

@Serializable
data object LaunchRoute

@Composable
internal fun MainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.background,
        ),
        navController = navController,
        startDestination = LaunchRoute,
    ) {
        launcher(navController)
        authNavigation(navController)
        homeNavigation(navController)
    }
}

private fun NavGraphBuilder.launcher(
    navController: NavHostController,
) {
    composable<LaunchRoute> {
        val isUserLoggedIn = koinInject<IsUserLoggedIn>()
        LaunchedEffect(isUserLoggedIn) {
            when (isUserLoggedIn()) {
                true -> navController.navigate(HomeRoute) {
                    popUpTo(LaunchRoute) { inclusive = true }
                }

                else -> navController.navigate(AuthRoute) {
                    popUpTo(LaunchRoute) { inclusive = true }
                }
            }
        }
    }
}
