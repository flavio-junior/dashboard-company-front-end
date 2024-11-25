package br.com.digital.store.domain.factory

import br.com.digital.store.components.model.Menu
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.box
import br.com.digital.store.composeapp.generated.resources.data_usage
import br.com.digital.store.composeapp.generated.resources.food
import br.com.digital.store.composeapp.generated.resources.list
import br.com.digital.store.composeapp.generated.resources.logout
import br.com.digital.store.composeapp.generated.resources.order
import br.com.digital.store.composeapp.generated.resources.refresh
import br.com.digital.store.composeapp.generated.resources.reservation
import br.com.digital.store.composeapp.generated.resources.settings
import br.com.digital.store.composeapp.generated.resources.shopping_cart
import br.com.digital.store.utils.ItemService
import br.com.digital.store.navigation.ItemNavigation
import br.com.digital.store.strings.StringsUtils.CATEGORIES
import br.com.digital.store.strings.StringsUtils.CHANGE_TO_OTHER_ACCOUNT
import br.com.digital.store.strings.StringsUtils.DASHBOARD
import br.com.digital.store.strings.StringsUtils.EXIT
import br.com.digital.store.strings.StringsUtils.FOOD
import br.com.digital.store.strings.StringsUtils.HISTORY
import br.com.digital.store.strings.StringsUtils.ITEMS
import br.com.digital.store.strings.StringsUtils.ORDER
import br.com.digital.store.strings.StringsUtils.PDV
import br.com.digital.store.strings.StringsUtils.PRODUCTS
import br.com.digital.store.strings.StringsUtils.RESERVATION
import br.com.digital.store.strings.StringsUtils.SETTINGS

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
        label = FOOD,
        route = ItemNavigation.FOOD.name
    ),
    Menu(
        icon = Res.drawable.box,
        label = PRODUCTS,
        route = ItemNavigation.PRODUCT.name
    ),
    Menu(
        icon = Res.drawable.order,
        label = ORDER,
        route = ItemNavigation.ORDER.name
    ),
    Menu(
        icon = Res.drawable.reservation,
        label = RESERVATION,
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
        label = DASHBOARD,
        route = ItemNavigation.DASHBOARD.name
    ),
    ItemService(
        label = PDV,
        route = ItemNavigation.PDV.name
    ),
    ItemService(
        label = CATEGORIES,
        route = ItemNavigation.CATEGORY.name
    ),
    ItemService(
        label = ITEMS,
        route = ItemNavigation.ITEM.name
    ),
    ItemService(
        label = FOOD,
        route = ItemNavigation.FOOD.name
    ),
    ItemService(
        label = PRODUCTS,
        route = ItemNavigation.PRODUCT.name
    ),
    ItemService(
        label = ORDER,
        route = ItemNavigation.ORDER.name
    ),
    ItemService(
        label = RESERVATION,
        route = ItemNavigation.RESERVATION.name
    )
)
