package br.com.digital.store.features.product.data.vo

import br.com.digital.store.features.category.data.vo.CategoryResponseVO

data class ProductResponseVO(
    val id: Long,
    val name: String,
    val description: String,
    val categories: List<CategoryResponseVO>? = null,
    val price: Double,
    val quantity: Int
)
