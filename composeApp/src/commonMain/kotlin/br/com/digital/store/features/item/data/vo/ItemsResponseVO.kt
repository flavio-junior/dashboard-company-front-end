package br.com.digital.store.features.item.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class ItemsResponseVO(
    val totalPages: Int,
    val content: List<ItemResponseVO>,
    val pageable: PageableVO
)
