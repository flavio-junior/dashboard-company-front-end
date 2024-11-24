package br.com.digital.store.domain.factory

import br.com.digital.store.components.model.Menu
import br.com.digital.store.navigation.ItemNavigation
import br.com.digital.store.strings.StringsUtils.CATEGORIES
import br.com.digital.store.strings.StringsUtils.CHANGE_TO_OTHER_ACCOUNT
import br.com.digital.store.strings.StringsUtils.EXIT
import br.com.digital.store.strings.StringsUtils.FOOD
import br.com.digital.store.strings.StringsUtils.HISTORY
import br.com.digital.store.strings.StringsUtils.ITEMS
import br.com.digital.store.strings.StringsUtils.ORDER
import br.com.digital.store.strings.StringsUtils.PDV
import br.com.digital.store.strings.StringsUtils.PRODUCTS
import br.com.digital.store.strings.StringsUtils.RESERVATION
import br.com.digital.store.strings.StringsUtils.SETTINGS
import lojavirtual.composeapp.generated.resources.Res
import lojavirtual.composeapp.generated.resources.box
import lojavirtual.composeapp.generated.resources.data_usage
import lojavirtual.composeapp.generated.resources.food
import lojavirtual.composeapp.generated.resources.list
import lojavirtual.composeapp.generated.resources.logout
import lojavirtual.composeapp.generated.resources.order
import lojavirtual.composeapp.generated.resources.refresh
import lojavirtual.composeapp.generated.resources.reservation
import lojavirtual.composeapp.generated.resources.settings
import lojavirtual.composeapp.generated.resources.shopping_cart

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
