package br.com.digital.store.features.category.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class CategoriesResponseVO(
    val totalPages: Int,
    val content: List<CategoryResponseVO>,
    val pageable: PageableVO
)
