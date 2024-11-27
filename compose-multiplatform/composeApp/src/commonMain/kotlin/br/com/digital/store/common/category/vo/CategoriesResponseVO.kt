package br.com.digital.store.common.category.vo

import br.com.digital.store.common.others.vo.PageableVO

data class CategoriesResponseVO(
    val totalPages: Int,
    val content: List<CategoryResponseVO>,
    val pageable: PageableVO
)
