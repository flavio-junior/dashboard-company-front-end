package br.com.digital.store.features.food.data.dto

import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class FoodResponseDTO(
    val id: Long,
    val name: String,
    val categories: List<CategoryResponseDTO>? = null,
    val price: Double
)
