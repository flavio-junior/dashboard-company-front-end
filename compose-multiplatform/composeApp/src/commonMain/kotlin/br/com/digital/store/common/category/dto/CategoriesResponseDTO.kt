package br.com.digital.store.common.category.dto

import br.com.digital.store.common.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDTO(
    val totalPages: Int,
    val content: List<CategoryResponseDTO>,
    val pageable: PageableDTO
)
