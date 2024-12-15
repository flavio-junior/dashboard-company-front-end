package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.type.TypeItem
import kotlinx.serialization.Serializable

@Serializable
data class ObjectRequestDTO(
    val identifier: Int,
    val quantity: Int,
    val type: TypeItem
)
