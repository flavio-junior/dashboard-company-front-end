package br.com.digital.store.features.food.data.vo

import br.com.digital.store.features.category.data.dto.CategoryRequestDTO

data class FoodResponseVO(
    val id: Long,
    val name: String,
    val description: String,
    val categories: List<CategoryRequestDTO>,
    val price: Double
)
