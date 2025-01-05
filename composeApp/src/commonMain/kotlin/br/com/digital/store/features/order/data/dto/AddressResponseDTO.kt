package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.status.AddressStatus
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponseDTO(
    val id: Long? = 0,
    val status: AddressStatus? = null,
    val complement: String? = "",
    val district: String? = "",
    val number: Int? = 0,
    val street: String? = ""
)
