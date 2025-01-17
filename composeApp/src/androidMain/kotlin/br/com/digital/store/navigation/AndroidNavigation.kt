package br.com.digital.store.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.digital.store.features.account.ui.SignInScreen
import br.com.digital.store.features.dashboard.ui.DashboardScreen
import br.com.digital.store.features.dashboard.ui.DeliveryScreen
import br.com.digital.store.features.dashboard.ui.OrderScreen
import br.com.digital.store.features.dashboard.ui.ReservationScreen
import br.com.digital.store.features.splash.SplashScreen

@Composable
fun AndroidNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = RouteApp.SplashScreen.item
) {
    NavHost(navController = navController, startDestination = startDestination) {
        splashScreenNavigation(navController = navController)
        signInNavigation(navController = navController)
        dashboardNavigation(navController = navController)
    }
}

private fun NavGraphBuilder.splashScreenNavigation(
    navController: NavHostController
) {
    composable(RouteApp.SplashScreen.item) {
        SplashScreen(
            goToSignInScreen = {
                navController.navigate(route = RouteApp.SignIn.item) {
                    popUpTo(RouteApp.SplashScreen.item) {
                        inclusive = true
                    }
                }
            },
            goToDashboardScreen = {
                navController.navigate(route = RouteApp.Dashboard.item) {
                    popUpTo(RouteApp.SplashScreen.item) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

private fun NavGraphBuilder.signInNavigation(
    navController: NavHostController
) {
    composable(RouteApp.SignIn.item) {
        SignInScreen(
            goToDashboardScreen = {
                navController.navigate(route = RouteApp.Dashboard.item) {
                    popUpTo(RouteApp.SignIn.item) {
                        inclusive = true
                    }
                }
            },
            goToSendRecoverTokenScreen = {},
            goToAlternativeRoutes = {}
        )
    }
}

private fun NavGraphBuilder.dashboardNavigation(
    navController: NavHostController
) {
    composable(RouteApp.Dashboard.item) {
        DashboardScreen(navGraph = navController)
    }
    composable(RouteApp.Delivery.item) {
        DeliveryScreen(
            goToBack = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Order.item) {
        OrderScreen(
            goToBack = {
                navController.popBackStack()
            }
        )
    }

    composable(RouteApp.Reservation.item) {
        ReservationScreen(
            goToBack = {
                navController.popBackStack()
            }
        )
    }
}