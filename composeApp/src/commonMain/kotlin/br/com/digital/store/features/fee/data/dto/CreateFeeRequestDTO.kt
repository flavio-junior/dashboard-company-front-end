package br.com.digital.store.features.fee.data.dto

import br.com.digital.store.features.fee.domain.type.Function
import kotlinx.serialization.Serializable

@Serializable
data class CreateFeeRequestDTO(
    val price: Double = 0.0,
    val assigned: Function? = null
)
