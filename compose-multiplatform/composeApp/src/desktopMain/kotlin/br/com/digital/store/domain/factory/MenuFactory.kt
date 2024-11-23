package br.com.digital.store.domain.factory

import br.com.digital.store.components.model.Menu
import br.com.digital.store.strings.StringsUtils.CATEGORIES
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
import lojavirtual.composeapp.generated.resources.reservation
import lojavirtual.composeapp.generated.resources.settings
import lojavirtual.composeapp.generated.resources.shopping_cart

val menus = listOf(
    Menu(
        icon = Res.drawable.shopping_cart,
        label = PDV
    ),
    Menu(
        icon = Res.drawable.box,
        label = CATEGORIES
    ),
    Menu(
        icon = Res.drawable.list,
        label = ITEMS
    ),
    Menu(
        icon = Res.drawable.food,
        label = FOOD
    ),
    Menu(
        icon = Res.drawable.box,
        label = PRODUCTS
    ),
    Menu(
        icon = Res.drawable.order,
        label = ORDER
    ),
    Menu(
        icon = Res.drawable.reservation,
        label = RESERVATION
    ),
    Menu(
        icon = Res.drawable.data_usage,
        label = HISTORY
    ),
    Menu(
        icon = Res.drawable.settings,
        label = SETTINGS
    ),
    Menu(
        icon = Res.drawable.logout,
        label = EXIT
    ),
)
