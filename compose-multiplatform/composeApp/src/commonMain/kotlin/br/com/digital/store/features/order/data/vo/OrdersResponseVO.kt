package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class OrdersResponseVO(
    val totalPages: Int,
    val content: List<OrderResponseVO>? = null,
    val pageable: PageableVO
)
