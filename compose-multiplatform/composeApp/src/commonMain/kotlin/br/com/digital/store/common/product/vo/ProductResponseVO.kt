package br.com.digital.store.common.product.vo

import br.com.digital.store.common.category.dto.CategoryResponseDTO

data class ProductResponseVO(
    val id: Long,
    val name: String,
    val description: String,
    val categories: List<CategoryResponseDTO>,
    val price: Double,
    val quantity: Int
)
