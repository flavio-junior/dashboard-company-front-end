package br.com.digital.store.features.food.data.vo

import br.com.digital.store.features.others.dto.PageableDTO

data class FoodsResponseVO(
    val totalPages: Int,
    val content: List<FoodResponseVO>,
    val pageable: PageableDTO
)
