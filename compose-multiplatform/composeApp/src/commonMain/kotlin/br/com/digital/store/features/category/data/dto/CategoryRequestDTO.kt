package br.com.digital.store.features.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequestDTO(
    val name: String
)
