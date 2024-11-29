package br.com.digital.store.features.product.data.dto

import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDTO(
    val id: Long,
    val name: String,
    val categories: List<CategoryResponseDTO> ? = null,
    val price: Double,
    val quantity: Int
)
