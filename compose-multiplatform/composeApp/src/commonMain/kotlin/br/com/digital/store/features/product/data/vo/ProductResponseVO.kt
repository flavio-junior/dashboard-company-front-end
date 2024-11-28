package br.com.digital.store.features.product.data.vo

import br.com.digital.store.features.category.data.dto.CategoryResponseDTO

data class ProductResponseVO(
    val id: Long,
    val name: String,
    val description: String,
    val categories: List<CategoryResponseDTO>,
    val price: Double,
    val quantity: Int
)
