package br.com.digital.store.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.digital.store.ui.CategoryScreen
import br.com.digital.store.ui.DashboardScreen
import br.com.digital.store.ui.FoodScreen
import br.com.digital.store.ui.HistoryScreen
import br.com.digital.store.ui.ItemScreen
import br.com.digital.store.ui.OrderScreen
import br.com.digital.store.ui.PdvScreen
import br.com.digital.store.ui.ProductScreen
import br.com.digital.store.ui.ReservationScreen
import br.com.digital.store.ui.SettingsScreen
import br.com.digital.store.ui.SignInScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String = RouteApp.SignIn.item
) {
    NavHost(navController = navController, startDestination = startDestination) {
        signInNavigation(navController = navController)
        dashboardNavigation(navController = navController)
    }
}

fun NavGraphBuilder.signInNavigation(
    navController: NavHostController
) {
    composable(RouteApp.SignIn.item) {
        SignInScreen(
            goToHomeScreen = {
                navController.navigate(route = RouteApp.Dashboard.item) {
                    popUpTo(RouteApp.SignIn.item) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

fun NavGraphBuilder.dashboardNavigation(
    navController: NavHostController
) {
    composable(RouteApp.Dashboard.item) {
        DashboardScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = { navigateBetweenMainRoutes(navGraph = navController, route = it) }
        )
    }

    composable(RouteApp.Pdv.item) {
        PdvScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Category.item) {
        CategoryScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Item.item) {
        ItemScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Food.item) {
        FoodScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Product.item) {
        ProductScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Order.item) {
        OrderScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Reservation.item) {
        ReservationScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.History.item) {
        HistoryScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
    composable(RouteApp.Settings.item) {
        SettingsScreen(
            goToBackScreen = {
                navController.popBackStack()
            }
        )
    }
}
