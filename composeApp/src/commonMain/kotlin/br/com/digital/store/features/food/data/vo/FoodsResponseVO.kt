package br.com.digital.store.features.food.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class FoodsResponseVO(
    val totalPages: Int,
    val content: List<FoodResponseVO>,
    val pageable: PageableVO
)
