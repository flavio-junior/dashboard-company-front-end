package br.com.digital.store.features.food.data.vo

import br.com.digital.store.features.category.data.vo.CategoryResponseVO

data class FoodResponseVO(
    val id: Long = 0,
    val name: String = "",
    val categories: List<CategoryResponseVO>? = null,
    val price: Double = 0.0
)
