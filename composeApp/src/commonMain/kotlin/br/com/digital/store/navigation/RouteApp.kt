package br.com.digital.store.navigation

sealed class RouteApp(val item: String) {
    data object SignIn : RouteApp(ItemNavigation.SIGN_IN.name)
    data object SendRecoverToken : RouteApp(ItemNavigation.SEND_RECOVER_TOKEN.name)
    data object CheckRecoverToken : RouteApp(ItemNavigation.CHECK_RECOVER_TOKEN.name)
    data object ResetPassword : RouteApp(ItemNavigation.RESET_PASSWORD.name)
    data object Dashboard : RouteApp(ItemNavigation.DASHBOARD.name)
    data object Pdv: RouteApp(ItemNavigation.PDV.name)
    data object Category: RouteApp(ItemNavigation.CATEGORY.name)
    data object Item: RouteApp(ItemNavigation.ITEM.name)
    data object Food: RouteApp(ItemNavigation.FOOD.name)
    data object Product: RouteApp(ItemNavigation.PRODUCT.name)
    data object Order: RouteApp(ItemNavigation.ORDER.name)
    data object Reservation: RouteApp(ItemNavigation.RESERVATION.name)
    data object REPORTS: RouteApp(ItemNavigation.REPORTS.name)
    data object History: RouteApp(ItemNavigation.HISTORY.name)
    data object Settings: RouteApp(ItemNavigation.SETTINGS.name)
}