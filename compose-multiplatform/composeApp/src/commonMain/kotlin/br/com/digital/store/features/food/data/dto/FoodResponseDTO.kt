package br.com.digital.store.features.food.data.dto

import br.com.digital.store.features.category.data.dto.CategoryRequestDTO
import kotlinx.serialization.Serializable

@Serializable
data class FoodResponseDTO(
    val id: Long,
    val name: String,
    val description: String,
    val categories: List<CategoryRequestDTO>,
    val price: Double
)
