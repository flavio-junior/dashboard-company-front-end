package br.com.digital.store.features.dashboard.ui

import androidx.annotation.StringRes
import br.com.digital.store.R

enum class BottomNavigationItem {
    PDV,
    ORDERS,
    SETTINGS
}

sealed class BottomNavigationRoute(
    @StringRes val label: Int,
    val icon: Int,
    val route: BottomNavigationItem
) {
    data object PDV : BottomNavigationRoute(
        R.string.pdv,
        R.drawable.shopping_cart,
        BottomNavigationItem.PDV
    )

    data object PendingOrders : BottomNavigationRoute(
        R.string.orders,
        R.drawable.order,
        BottomNavigationItem.ORDERS
    )

    data object Settings : BottomNavigationRoute(
        R.string.SETTINGS,
        R.drawable.settings,
        BottomNavigationItem.SETTINGS
    )
}
