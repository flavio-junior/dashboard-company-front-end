package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.others.Action
import br.com.digital.store.features.order.domain.status.ObjectStatus
import kotlinx.serialization.Serializable

@Serializable
data class UpdateObjectRequestDTO(
    var action: Action,
    var overview: Long = 0,
    var status: ObjectStatus? = null,
    var quantity: Int = 0
)
