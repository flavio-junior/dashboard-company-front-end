package br.com.digital.store.features.fee.data.dto

import br.com.digital.store.features.fee.domain.type.Function
import kotlinx.serialization.Serializable

@Serializable
data class FeeResponseOrderDTO(
    val id: Long? = 0,
    val percentage: Int? = 0,
    var assigned: Function? = null,
    var author: AuthorResponseDTO? = null
)
