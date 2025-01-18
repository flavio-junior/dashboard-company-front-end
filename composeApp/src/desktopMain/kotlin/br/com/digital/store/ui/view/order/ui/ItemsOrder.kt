package br.com.digital.store.ui.view.order.ui

import br.com.digital.store.features.order.utils.OrderUtils.ADD_MORE_ITEMS_ORDER
import br.com.digital.store.features.order.utils.OrderUtils.ADD_RESERVATIONS
import br.com.digital.store.features.order.utils.OrderUtils.ALTER_ADDRESS
import br.com.digital.store.features.order.utils.OrderUtils.DETAILS_ITEM
import br.com.digital.store.features.order.utils.OrderUtils.DETAILS_ORDER
import br.com.digital.store.features.order.utils.OrderUtils.PENDING_ORDERS

enum class ItemsOrder(val text: String) {
    PendingOrders(text = PENDING_ORDERS),
    DetailsOrder(text = DETAILS_ORDER),
    DetailsItem(text = DETAILS_ITEM),
    AlterAddress(text = ALTER_ADDRESS),
    AddItems(text = ADD_MORE_ITEMS_ORDER),
    IncrementMoreReservationsOrder(text  = ADD_RESERVATIONS)
}
