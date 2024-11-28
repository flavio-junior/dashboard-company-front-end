package br.com.digital.store.common.item.dto

import br.com.digital.store.common.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ItemsResponseDTO(
    val totalPages: Int,
    val content: List<ItemResponseDTO>,
    val pageable: PageableDTO
)
