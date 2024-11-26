package br.com.digital.store.common.category.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDTO(
    val content: List<CategoryResponseDTO>
)
