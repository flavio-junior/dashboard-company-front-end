package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.status.AddressStatus
import kotlinx.serialization.Serializable

@Serializable
data class UpdateStatusDeliveryRequestDTO(
    val status: AddressStatus
)
