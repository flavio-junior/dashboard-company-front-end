package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.status.ObjectStatus

data class OverviewResponseVO(
    var id: Long = 0,
    var createdAt: String? = null,
    var status: ObjectStatus? = null,
    var quantity: Int = 0
)
