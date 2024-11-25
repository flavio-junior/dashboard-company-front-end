package br.com.digital.store.common.food.vo

import br.com.digital.store.common.category.dto.CategoryRequestDTO

data class FoodResponseVO(
    val id: Long,
    val name: String,
    val description: String,
    val categories: List<CategoryRequestDTO>,
    val price: Double
)
