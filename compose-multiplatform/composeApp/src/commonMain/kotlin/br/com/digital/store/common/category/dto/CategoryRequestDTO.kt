package br.com.digital.store.common.category.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequestDTO(
    val name: String
)
