package br.com.digital.store.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.digital.store.ui.view.category.CategoryScreen
import br.com.digital.store.ui.view.dashboard.DashboardScreen
import br.com.digital.store.ui.view.food.FoodScreen
import br.com.digital.store.ui.view.history.HistoryScreen
import br.com.digital.store.ui.view.item.ItemScreen
import br.com.digital.store.ui.view.order.OrderScreen
import br.com.digital.store.ui.view.pdv.PdvScreen
import br.com.digital.store.ui.view.product.ProductScreen
import br.com.digital.store.ui.view.reservation.ReservationScreen
import br.com.digital.store.ui.view.settings.SettingsScreen
import br.com.digital.store.ui.view.login.SignInScreen

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
            goToDashboardScreen = {
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
