package br.com.digital.store.navigation

sealed class RouteApp(val item: String) {
    data object SignIn : RouteApp(item = ItemNavigation.SIGN_IN.name)
    data object SendRecoverToken : RouteApp(item = ItemNavigation.SEND_RECOVER_TOKEN.name)
    data object CheckRecoverToken : RouteApp(item = ItemNavigation.CHECK_RECOVER_TOKEN.name)
    data object ResetPassword : RouteApp(item = ItemNavigation.RESET_PASSWORD.name)
    data object Dashboard : RouteApp(item = ItemNavigation.DASHBOARD.name)
    data object Pdv: RouteApp(item = ItemNavigation.PDV.name)
    data object Category: RouteApp(item = ItemNavigation.CATEGORY.name)
    data object Item: RouteApp(item = ItemNavigation.ITEM.name)
    data object Food: RouteApp(item = ItemNavigation.FOOD.name)
    data object Product: RouteApp(item = ItemNavigation.PRODUCT.name)
    data object Order: RouteApp(item = ItemNavigation.ORDER.name)
    data object Reservation: RouteApp(item = ItemNavigation.RESERVATION.name)
    data object Payment: RouteApp(item = ItemNavigation.PAYMENT.name)
    data object Report: RouteApp(item = ItemNavigation.REPORT.name)
    data object History: RouteApp(item = ItemNavigation.HISTORY.name)
    data object Settings: RouteApp(item = ItemNavigation.SETTINGS.name)
}