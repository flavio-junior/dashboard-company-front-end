package br.com.digital.store.common.item.vo

import br.com.digital.store.common.others.vo.PageableVO

data class ItemsResponseVO(
    val totalPages: Int,
    val content: List<ItemResponseVO>,
    val pageable: PageableVO
)
