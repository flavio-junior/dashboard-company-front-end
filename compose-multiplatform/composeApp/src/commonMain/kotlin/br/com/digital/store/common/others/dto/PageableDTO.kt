package br.com.digital.store.common.others.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageableDTO(
    val pageNumber: Int
)
