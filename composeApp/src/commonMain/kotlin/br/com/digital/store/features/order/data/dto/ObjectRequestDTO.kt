package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.type.TypeItem
import kotlinx.serialization.Serializable

@Serializable
data class ObjectRequestDTO(
    val name: String = "",
    val identifier: Long = 0,
    val actualQuantity: Int = 0,
    var quantity: Int = 0,
    val type: TypeItem? = null
)
