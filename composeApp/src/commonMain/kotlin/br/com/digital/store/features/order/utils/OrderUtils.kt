package br.com.digital.store.features.order.utils

import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.status.ObjectStatus
import br.com.digital.store.features.order.utils.OrderUtils.CREDIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.DEBIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.MONEY
import br.com.digital.store.features.order.utils.OrderUtils.PIX

object OrderUtils {
    const val ACTUAL_ADDRESS = "Endereço Atual:"
    const val PENDING_ORDERS = "Pedidos Pendentes"
    const val DETAILS_ORDER = "Detalhes do Pedido"
    const val ALTER_ADDRESS = "Alterar Endereço"
    const val EMPTY_LIST_ORDERS = "Nenhum Pedido Encontrado!"
    const val TOTAL_DELIVERY = "Total de Items Para Entrega:"
    const val TOTAL_NUMBER_ORDERS = "Número Total de Pedidos:"
    const val TOTAL_NUMBER_RESERVATIONS = "Número Total de Reservas:"
    const val NUMBER_RESERVATIONS = "Número de reservas:"
    const val NUMBER_ITEMS = "Número de itens:"
    const val CANCEL_ORDER = "Cancelar Pedido"
    const val INCREMENT_ITEM = "Adicionar itens ao pedido?"
    const val DECREMENT_ITEM = "Remover itens do pedido?"
    const val UPDATE_STATUS = "Atualizar Status do Pedido?"
    const val DELETE_ITEM = "Apagar Item"
    const val DELETE_OBJECT = "Apagar Item do Pedido?"
    const val ADD_MORE_ITEMS_ORDER = "Adicionar Itens no Pedido"
    const val CLOSE_ORDER = "Fechar Pedido"
    const val SELECTED_TYPE_PAYMENT = "Selecione o Tipo de Pagamento!"
    const val TYPE_OF_PAYMENT = "Tipo de Pagamento:"
    const val NO_SELECTED_ITEMS = "Nenhum item selecionado!"
    const val CREDIT_CAD = "Cartão de Crédito"
    const val DEBIT_CAD = "Cartão de Débito"
    const val MONEY = "Dinheiro"
    const val PIX = "PIX"
    const val SHOPPING_CART = "Carrinho de Compras"
    const val DELIVERY = "Delivery"
    const val RESERVATION = "Reserva"
}

val typePayment = listOf(
    CREDIT_CAD,
    DEBIT_CAD,
    MONEY,
    PIX
)

fun countPendingObjects(order: OrderResponseVO): String {
    val numberObjects = order.objects?.count { it.status == ObjectStatus.PENDING } ?: 0
    return numberObjects.toString()
}
