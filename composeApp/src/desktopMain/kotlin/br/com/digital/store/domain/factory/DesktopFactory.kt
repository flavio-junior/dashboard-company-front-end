package br.com.digital.store.domain.factory

import br.com.digital.store.components.model.Menu
import br.com.digital.store.components.strings.StringsUtils.CATEGORIES
import br.com.digital.store.components.strings.StringsUtils.CHANGE_TO_OTHER_ACCOUNT
import br.com.digital.store.components.strings.StringsUtils.DASHBOARD
import br.com.digital.store.components.strings.StringsUtils.EXIT
import br.com.digital.store.components.strings.StringsUtils.FOODS
import br.com.digital.store.components.strings.StringsUtils.HISTORY
import br.com.digital.store.components.strings.StringsUtils.ITEMS
import br.com.digital.store.components.strings.StringsUtils.ORDERS
import br.com.digital.store.components.strings.StringsUtils.PDV
import br.com.digital.store.components.strings.StringsUtils.RESERVATIONS
import br.com.digital.store.components.strings.StringsUtils.SETTINGS
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.box
import br.com.digital.store.composeapp.generated.resources.data_usage
import br.com.digital.store.composeapp.generated.resources.food
import br.com.digital.store.composeapp.generated.resources.home_pin
import br.com.digital.store.composeapp.generated.resources.list
import br.com.digital.store.composeapp.generated.resources.logout
import br.com.digital.store.composeapp.generated.resources.order
import br.com.digital.store.composeapp.generated.resources.refresh
import br.com.digital.store.composeapp.generated.resources.reservation
import br.com.digital.store.composeapp.generated.resources.settings
import br.com.digital.store.composeapp.generated.resources.shopping_cart
import br.com.digital.store.features.product.utils.ProductUtils.PRODUCTS
import br.com.digital.store.navigation.ItemNavigation
import br.com.digital.store.utils.ItemService

val menus = listOf(
    Menu(
        icon = Res.drawable.shopping_cart,
        label = PDV,
        route = ItemNavigation.PDV.name
    ),
    Menu(
        icon = Res.drawable.box,
        label = CATEGORIES,
        route = ItemNavigation.CATEGORY.name
    ),
    Menu(
        icon = Res.drawable.list,
        label = ITEMS,
        route = ItemNavigation.ITEM.name
    ),
    Menu(
        icon = Res.drawable.food,
        label = FOODS,
        route = ItemNavigation.FOOD.name
    ),
    Menu(
        icon = Res.drawable.box,
        label = PRODUCTS,
        route = ItemNavigation.PRODUCT.name
    ),
    Menu(
        icon = Res.drawable.order,
        label = ORDERS,
        route = ItemNavigation.ORDER.name
    ),
    Menu(
        icon = Res.drawable.reservation,
        label = RESERVATIONS,
        route = ItemNavigation.RESERVATION.name
    ),
    Menu(
        icon = Res.drawable.data_usage,
        label = HISTORY,
        route = ItemNavigation.HISTORY.name
    ),
    Menu(
        icon = Res.drawable.settings,
        label = SETTINGS,
        route = ItemNavigation.SETTINGS.name
    ),
    Menu(
        icon = Res.drawable.refresh,
        label = CHANGE_TO_OTHER_ACCOUNT,
        route = ItemNavigation.CHANGE_TO_OTHER_ACCOUNT.name
    ),
    Menu(
        icon = Res.drawable.logout,
        label = EXIT,
        route = ItemNavigation.EXIT.name
    )
)

val availableServices = listOf(
    ItemService(
        icon = Res.drawable.home_pin,
        label = DASHBOARD,
        route = ItemNavigation.DASHBOARD.name
    ),
    ItemService(
        icon = Res.drawable.shopping_cart,
        label = PDV,
        route = ItemNavigation.PDV.name
    ),
    ItemService(
        icon = Res.drawable.box,
        label = CATEGORIES,
        route = ItemNavigation.CATEGORY.name
    ),
    ItemService(
        icon = Res.drawable.list,
        label = ITEMS,
        route = ItemNavigation.ITEM.name
    ),
    ItemService(
        icon = Res.drawable.food,
        label = FOODS,
        route = ItemNavigation.FOOD.name
    ),
    ItemService(
        icon = Res.drawable.box,
        label = PRODUCTS,
        route = ItemNavigation.PRODUCT.name
    ),
    ItemService(
        icon = Res.drawable.order,
        label = ORDERS,
        route = ItemNavigation.ORDER.name
    ),
    ItemService(
        icon = Res.drawable.reservation,
        label = RESERVATIONS,
        route = ItemNavigation.RESERVATION.name
    )
)
