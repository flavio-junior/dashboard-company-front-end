package br.com.digital.store.features.payment.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class PaymentsResponseDTO(
    val totalPages: Int,
    val content: List<PaymentResponseDTO>,
    val pageable: PageableDTO
)
