package br.com.digital.store.features.category.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDTO(
    val totalPages: Int,
    val content: List<CategoryResponseDTO>,
    val pageable: PageableDTO
)
