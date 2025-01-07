package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.status.ObjectStatus
import br.com.digital.store.features.order.domain.type.TypeItem

data class ObjectResponseVO(
    val id: Long = 0,
    val identifier: Int = 0,
    val type: TypeItem? = null,
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val total: Double = 0.0,
    val status: ObjectStatus? = null,
    val overview: List<OverviewResponseVO>? = null
)
