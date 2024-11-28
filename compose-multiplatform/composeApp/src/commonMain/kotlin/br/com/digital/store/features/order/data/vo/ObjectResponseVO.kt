package br.com.digital.store.features.order.data.vo

data class ObjectResponseVO(
    val id: Int,
    val identifier: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val total: Double,
    val type: String
)
