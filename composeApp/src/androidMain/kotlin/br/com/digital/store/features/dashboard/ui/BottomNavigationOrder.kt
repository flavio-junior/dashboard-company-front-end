package br.com.digital.store.features.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.digital.store.navigation.RouteApp

@Composable
fun BottomNavigationOrder(
    modifier: Modifier,
    navController: NavHostController,
    navGraph: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationRoute.PDV.route.name,
        modifier = modifier
    ) {
        composable(BottomNavigationRoute.PDV.route.name) {
            PdvScreen(navGraph = navGraph)
        }
        composable(BottomNavigationRoute.PendingOrders.route.name) {
            PendingOrdersScreen()
        }
        composable(BottomNavigationRoute.Settings.route.name) {
            SettingsScreen(
                goToLoginScreen = {
                    navGraph.navigate(route = RouteApp.SignIn.item)
                }
            )
        }
    }
}
