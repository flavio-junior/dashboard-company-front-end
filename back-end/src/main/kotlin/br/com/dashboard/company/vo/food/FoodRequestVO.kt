package br.com.dashboard.company.vo.food

import br.com.dashboard.company.vo.category.CategoryResponseVO

data class FoodRequestVO(
    var name: String = "",
    var description: String = "",
    var categories: MutableList<CategoryResponseVO>? = null,
    var price: Double = 0.0,
    var quantity: Int = 0
)
