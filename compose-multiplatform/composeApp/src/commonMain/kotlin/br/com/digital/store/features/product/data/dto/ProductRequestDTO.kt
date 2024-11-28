package br.com.digital.store.features.product.data.dto

import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductRequestDTO(
    val name: String,
    val description: String,
    val categories: List<CategoryResponseDTO>,
    val price: Double,
    val quantity: Int
)
