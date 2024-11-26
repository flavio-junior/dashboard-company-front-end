package br.com.digital.store.common.category.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditCategoryRequestDTO(
    val id: Long,
    val name: String
)
