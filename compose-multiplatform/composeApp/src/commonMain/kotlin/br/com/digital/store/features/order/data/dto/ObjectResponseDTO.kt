package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.status.ObjectStatus
import br.com.digital.store.features.order.domain.type.TypeItem
import kotlinx.serialization.Serializable

@Serializable
data class ObjectResponseDTO(
    val id: Long,
    val identifier: Int,
    val type: TypeItem,
    val name: String,
    val price: Double,
    val quantity: Int,
    val total: Double,
    val status: ObjectStatus? = null
)
