package br.com.digital.store.navigation

import androidx.navigation.NavHostController

fun navigateBetweenMainRoutes(
    navGraph: NavHostController,
    route: String
) {
    navigateBetweenDashboardScreen(navGraph = navGraph, route = route)
}

private fun navigateBetweenDashboardScreen(
    navGraph: NavHostController,
    route: String
) {
    when (route) {
        ItemNavigation.PDV.name ->
            navGraph.navigate(route = RouteApp.Pdv.item)

        ItemNavigation.CATEGORY.name ->
            navGraph.navigate(route = RouteApp.Category.item)

        ItemNavigation.ITEM.name ->
            navGraph.navigate(route = RouteApp.Item.item)

        ItemNavigation.FOOD.name ->
            navGraph.navigate(route = RouteApp.Food.item)

        ItemNavigation.PRODUCT.name ->
            navGraph.navigate(route = RouteApp.Product.item)

        ItemNavigation.ORDER.name ->
            navGraph.navigate(route = RouteApp.Order.item)

        ItemNavigation.RESERVATION.name ->
            navGraph.navigate(route = RouteApp.Reservation.item)

        ItemNavigation.PAYMENT.name ->
            navGraph.navigate(route = RouteApp.Payment.item)

        ItemNavigation.REPORT.name ->
            navGraph.navigate(route = RouteApp.Report.item)

        ItemNavigation.HISTORY.name ->
            navGraph.navigate(route = RouteApp.History.item)

        ItemNavigation.SETTINGS.name ->
            navGraph.navigate(route = RouteApp.Settings.item)

        ItemNavigation.SIGN_IN.name ->
            navGraph.navigate(route = RouteApp.SignIn.item)
    }
}