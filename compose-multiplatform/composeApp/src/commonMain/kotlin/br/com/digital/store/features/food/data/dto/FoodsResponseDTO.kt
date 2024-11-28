package br.com.digital.store.features.food.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class FoodsResponseDTO(
    val totalPages: Int,
    val content: List<FoodResponseDTO>,
    val pageable: PageableDTO
)
