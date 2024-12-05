package br.com.digital.store.features.order.ui.view

import br.com.digital.store.features.order.utils.OrderUtils.CREATE_NEW_ORDER
import br.com.digital.store.features.order.utils.OrderUtils.DETAILS_ORDER
import br.com.digital.store.features.order.utils.OrderUtils.ORDERS_COMPLETED
import br.com.digital.store.features.order.utils.OrderUtils.PENDING_ORDERS

enum class ItemsOrder(val text: String) {
    PendingOrders(text = PENDING_ORDERS),
    OrdersCompleted(text = ORDERS_COMPLETED),
    DetailsOrder(text = DETAILS_ORDER),
    CreateNewOrder(text = CREATE_NEW_ORDER)
}
