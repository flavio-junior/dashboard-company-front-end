package br.com.dashboard.company.vo.order

import br.com.dashboard.company.vo.category.CategoryResponseVO
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class OrderResponseVO(
    var id: Long = 0,
    @JsonProperty(value = "created_at")
    var createdAt: Instant? = null,
    var name: String = "",
    var description: String = "",
    var categories: MutableList<CategoryResponseVO>? = null,
    var price: Double = 0.0,
    var quantity: Int = 0
)
