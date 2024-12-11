package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.others.Action
import kotlinx.serialization.Serializable

@Serializable
data class UpdateObjectRequestDTO(
    var action: Action,
    var quantity: Int = 0
)
