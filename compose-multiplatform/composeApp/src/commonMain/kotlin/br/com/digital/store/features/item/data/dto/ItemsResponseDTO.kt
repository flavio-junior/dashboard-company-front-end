package br.com.digital.store.features.item.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ItemsResponseDTO(
    val totalPages: Int,
    val content: List<ItemResponseDTO>,
    val pageable: PageableDTO
)
