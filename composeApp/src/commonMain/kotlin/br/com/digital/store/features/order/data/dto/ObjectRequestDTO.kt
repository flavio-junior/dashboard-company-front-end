package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.type.TypeItem
import kotlinx.serialization.Serializable

@Serializable
data class ObjectRequestDTO(
    val name: String,
    val identifier: Long,
    var quantity: Int,
    val type: TypeItem
)
