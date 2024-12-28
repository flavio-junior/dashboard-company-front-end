package br.com.digital.store.features.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryNameRequestDTO(
    val name: String
)
