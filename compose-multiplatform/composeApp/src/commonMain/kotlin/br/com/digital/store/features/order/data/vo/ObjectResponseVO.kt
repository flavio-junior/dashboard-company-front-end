package br.com.digital.store.features.order.data.vo

import br.com.digital.store.features.order.domain.type.TypeItem

data class ObjectResponseVO(
    val id: Int,
    val identifier: Int,
    val type: TypeItem,
    val name: String,
    val price: Double,
    val quantity: Int,
    val total: Double
)
