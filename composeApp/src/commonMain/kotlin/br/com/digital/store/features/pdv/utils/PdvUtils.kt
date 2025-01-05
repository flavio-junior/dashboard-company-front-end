package br.com.digital.store.features.pdv.utils

import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.delivery_truck
import br.com.digital.store.composeapp.generated.resources.order
import br.com.digital.store.composeapp.generated.resources.reservation
import br.com.digital.store.composeapp.generated.resources.shopping_cart
import br.com.digital.store.features.order.utils.OrderUtils.PENDING_ORDERS
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_DELIVERY_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_RESERVATION
import br.com.digital.store.features.pdv.utils.PdvUtils.SHOPPING_CART
import br.com.digital.store.utils.NumbersUtils
import org.jetbrains.compose.resources.DrawableResource

object PdvUtils {
    const val PDV = "Ponto de Vendas"
    const val SHOPPING_CART = "Carrinho de Compras"
    const val CREATE_DELIVERY_ORDER = "Criar Pedido Para Entrega"
    const val CREATE_ORDER = "Criar Pedido Para Retirada"
    const val CREATE_RESERVATION = "Criar Reserva"
    const val SELECT_ORDER = "Selecionar itens para retirada:"
    const val SELECT_RESERVATIONS_TO_ORDER = "Selecionar reservas:"
    const val SELECT_RESERVATIONS = "Selecionar itens para a reserva:"
}

enum class TypeNavigation {
    NAVIGATION,
    TAB
}

data class TypeOrder(
    val icon: DrawableResource,
    val label: String,
    val navigation: TypeNavigation = TypeNavigation.TAB,
    val count: Int
)

val selectTypeOrder = listOf(
    TypeOrder(
        icon = Res.drawable.shopping_cart,
        label = SHOPPING_CART,
        count = NumbersUtils.NUMBER_ONE
    ),
    TypeOrder(
        icon = Res.drawable.delivery_truck,
        label = CREATE_DELIVERY_ORDER,
        count = NumbersUtils.NUMBER_TWO
    ),
    TypeOrder(
        icon = Res.drawable.order,
        label = CREATE_ORDER,
        count = NumbersUtils.NUMBER_THREE
    ),
    TypeOrder(
        icon = Res.drawable.reservation,
        label = CREATE_RESERVATION,
        count = NumbersUtils.NUMBER_FOUR
    ),
    TypeOrder(
        icon = Res.drawable.order,
        label = PENDING_ORDERS,
        navigation = TypeNavigation.NAVIGATION,
        count = NumbersUtils.NUMBER_ONE
    )
)
