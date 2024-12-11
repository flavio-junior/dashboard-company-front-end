package br.com.digital.store.features.order.utils

import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.status.ObjectStatus

object OrderUtils {
    const val PENDING_ORDERS = "Pedidos Pendentes"
    const val ORDERS_COMPLETED = "Pedidos Concluídos"
    const val DETAILS_ORDER = "Detalhes do Pedido"
    const val CREATE_NEW_ORDER = "Criar Novo Pedido"
    const val UPDATE_ORDER = "Atualizar Pedido:"
    const val EMPTY_LIST_ORDERS = "Nenhum Pedido Encontrado!"
    const val NUMBER_ORDER = "Número do Pedido:"
    const val TOTAL_DELIVERY = "Total de Items Para Entrega:"
    const val TOTAL_NUMBER_ORDERS = "Número Total de Pedidos:"
    const val TOTAL_NUMBER_RESERVATIONS = "Número Total de Reservas:"
    const val NUMBER_RESERVATIONS = "Número de reservas:"
    const val NUMBER_ITEMS = "Número de itens:"
    const val DELETE_ORDER = "Apagar Pedido"
    const val INCREMENT_ITEM = "Adicionar itens ao pedido?"
    const val DECREMENT_ITEM = "Remover itens do pedido?"
    const val UPDATE_STATUS = "Atualizar Status do Pedido?"
    const val DELETE_ITEM = "Apagar Item"
    const val DELETE_OBJECT = "Apagar Item do Pedido?"
}

fun countPendingObjects(order: OrderResponseVO): String {
    val numberObjects = order.objects?.count { it.status == ObjectStatus.PENDING } ?: 0
    return numberObjects.toString()
}
