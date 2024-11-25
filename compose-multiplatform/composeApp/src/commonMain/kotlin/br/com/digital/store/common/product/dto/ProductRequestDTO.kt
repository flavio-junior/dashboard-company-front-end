package br.com.digital.store.common.product.dto

import br.com.digital.store.common.category.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductRequestDTO(
    val name: String,
    val description: String,
    val categories: List<CategoryResponseDTO>,
    val price: Double,
    val quantity: Int
)
