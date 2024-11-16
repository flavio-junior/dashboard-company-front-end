package br.com.dashboard.company.vo.reservation

import br.com.dashboard.company.vo.category.CategoryResponseVO

data class ReservationRequestVO(
    var name: String = "",
    var description: String = "",
    var categories: MutableList<CategoryResponseVO>? = null,
    var price: Double = 0.0,
    var quantity: Int = 0
)
