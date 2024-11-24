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
            goToNextScreen = { navigateBetweenMainRoutes(navGraph = navController, route = it) }
        )
    }

    composable(RouteApp.Pdv.item) {
        PdvScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = RouteApp.Pdv.item,
                    nextScreen = it
                )
            }
        )
    }
    composable(RouteApp.Category.item) {
        CategoryScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = RouteApp.Category.item,
                    nextScreen = it
                )
            }
        )
    }
    composable(RouteApp.Item.item) {
        ItemScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = RouteApp.Item.item,
                    nextScreen = it
                )
            }
        )
    }
    composable(RouteApp.Food.item) {
        FoodScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = RouteApp.Food.item,
                    nextScreen = it
                )
            }
        )
    }
    composable(RouteApp.Product.item) {
        ProductScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = RouteApp.Product.item,
                    nextScreen = it
                )
            }
        )
    }
    composable(RouteApp.Order.item) {
        OrderScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = RouteApp.Order.item,
                    nextScreen = it
                )
            }
        )
    }
    composable(RouteApp.Reservation.item) {
        ReservationScreen(
            goToBackScreen = {
                navController.popBackStack()
            },
            goToNextScreen = {
                goToNextScreen(
                    navHostController = navController,
                    currentScreen = RouteApp.Reservation.item,
                    nextScreen = it
                )
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
