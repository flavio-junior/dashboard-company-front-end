package br.com.digital.store.features.order.data.dto

import br.com.digital.store.features.order.domain.others.Action

data class UpdateObjectRequestDTO(
    var action: Action,
    var quantity: Int = 0
)
