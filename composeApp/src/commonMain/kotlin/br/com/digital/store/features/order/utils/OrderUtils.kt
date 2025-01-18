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
    const val DETAILS_ITEM = "Detalhes do Item"
    const val ALTER_ADDRESS = "Alterar Endereço"
    const val EMPTY_LIST_ORDERS = "Nenhum Pedido Encontrado!"
    const val TOTAL_DELIVERY = "Total de Items Para Entrega:"
    const val TOTAL_NUMBER_ORDERS = "Número Total de Pedidos:"
    const val TOTAL_NUMBER_RESERVATIONS = "Número Total de Reservas:"
    const val NUMBER_RESERVATIONS = "Número de reservas:"
    const val NUMBER_ITEMS = "Número de itens:"
    const val CANCEL_ORDER = "Cancelar Pedido"
    const val INCREMENT_ITEM = "Adicionar itens ao pedido?"
    const val UPDATE_STATUS_DELIVERY = "Atualizar Status de Entrega?"
    const val UPDATE_STATUS_ITEM = "Atualizar Status do Item?"
    const val DELETE_OBJECT = "Apagar Item do Pedido?"
    const val ADD_MORE_ITEMS_ORDER = "Adicionar Itens no Pedido"
    const val ADD_RESERVATIONS = "Adicionar Reservas"
    const val CLOSE_ORDER = "Fechar Pedido"
    const val SELECTED_TYPE_PAYMENT = "Selecione o Tipo de Pagamento!"
    const val TYPE_OF_PAYMENT = "Tipo de Pagamento:"
    const val NO_SELECTED_ITEMS = "Nenhum item selecionado!"
    const val CREDIT_CAD = "Cartão de Crédito"
    const val DEBIT_CAD = "Cartão de Débito"
    const val MONEY = "Dinheiro"
    const val PIX = "PIX"
    const val CREDIT_CAD_LATIN = "Cartao de Credito"
    const val DEBIT_CAD_LATIN = "Cartao de Debito"
    const val SHOPPING_CART = "Carrinho de Compras"
    const val BUY_PRODUCTS = "Compras"
    const val DELIVERY = "Delivery"
    const val RESERVATION = "Reserva"
    const val RESUME_ITEM = "Resumo do Item:"
    const val UPDATE_RESUME = "Atualizar Status do Resumo?"
    const val RESUME_SELECTED = "Resumo Selecionado:"
    const val DELETE_RESUME = "Apagar Resumo?"
    const val DIGIT_ONE_VALUE_TO_DISCOUNT = "Digite o valor do disconto!"
    const val VALUE_DISCOUNT = "Valor do Desconto:"
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

fun centerText(text: String, totalWidth: Int = 32): String {
    val padding = (totalWidth - text.length) / 2
    return " ".repeat(padding.coerceAtLeast(0)) + text
}

fun alignLeftRight(left: String, right: String, totalWidth: Int = 32): String {
    val padding = totalWidth - left.length - right.length
    return if (padding > 0) {
        left + " ".repeat(padding) + right
    } else {
        left + right.takeLast(totalWidth - left.length)
    }
}
