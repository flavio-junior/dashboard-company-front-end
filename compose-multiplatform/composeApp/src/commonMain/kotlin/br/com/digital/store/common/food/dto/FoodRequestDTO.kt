package br.com.digital.store.common.food.dto

import br.com.digital.store.common.category.dto.CategoryRequestDTO
import kotlinx.serialization.Serializable

@Serializable
data class FoodRequestDTO(
    val name: String,
    val description: String,
    val categories: List<CategoryRequestDTO>,
    val price: Double
)
