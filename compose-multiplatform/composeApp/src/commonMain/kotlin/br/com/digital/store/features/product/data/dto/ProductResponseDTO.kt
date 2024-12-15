package br.com.digital.store.features.product.data.dto

import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDTO(
    val id: Long = 0,
    val name: String = "",
    val categories: List<CategoryResponseDTO> ? = null,
    val price: Double = 0.0,
    val quantity: Int = 0
)
