package br.com.dashboard.company.vo.product

import br.com.dashboard.company.vo.category.CategoryResponseVO
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ProductResponseVO(
    var id: Long = 0,
    @JsonProperty(value = "created_at")
    var createdAt: LocalDateTime? = null,
    var name: String = "",
    var categories: MutableList<CategoryResponseVO>? = null,
    var price: Double = 0.0,
    var quantity: Int = 0
)
