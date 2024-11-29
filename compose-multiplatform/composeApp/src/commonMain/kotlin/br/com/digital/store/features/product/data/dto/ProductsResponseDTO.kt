package br.com.digital.store.features.product.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseDTO(
    val totalPages: Int,
    val content: List<ProductResponseDTO>,
    val pageable: PageableDTO
)
