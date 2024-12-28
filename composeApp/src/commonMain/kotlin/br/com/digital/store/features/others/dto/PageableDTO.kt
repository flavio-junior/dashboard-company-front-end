package br.com.digital.store.features.others.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageableDTO(
    val pageNumber: Int
)
