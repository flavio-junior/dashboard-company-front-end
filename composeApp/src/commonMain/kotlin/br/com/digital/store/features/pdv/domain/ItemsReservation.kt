package br.com.digital.store.features.pdv.domain

import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_DELIVERY_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_ORDER
import br.com.digital.store.features.pdv.utils.PdvUtils.CREATE_RESERVATION
import br.com.digital.store.features.pdv.utils.PdvUtils.PDV

enum class ItemsPVD(val text: String) {
    Pdv(text = PDV),
    CreateDeliveryOrder(text = CREATE_DELIVERY_ORDER),
    CreateOrder(text = CREATE_ORDER),
    CreateReservation(text = CREATE_RESERVATION)
}
