package br.com.digital.store.features.product.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class ProductsResponseVO(
    val totalPages: Int,
    val content: List<ProductResponseVO>,
    val pageable: PageableVO
)
