package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.status.ObjectStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverviewResponseDTO(
    var id: Long = 0,
    @SerialName("created_at")
    var createdAt: String? = "",
    var status: ObjectStatus? = null,
    var quantity: Int = 0
)
