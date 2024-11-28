package br.com.digital.store.features.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditCategoryRequestDTO(
    val id: Long,
    val name: String
)
