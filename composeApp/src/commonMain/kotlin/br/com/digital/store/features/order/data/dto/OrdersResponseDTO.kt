package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class OrdersResponseDTO(
    val totalPages: Int,
    val content: List<OrderResponseDTO>,
    val pageable: PageableDTO
)
