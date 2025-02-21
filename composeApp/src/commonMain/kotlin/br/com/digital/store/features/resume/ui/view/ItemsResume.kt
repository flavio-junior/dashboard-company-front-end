package br.com.digital.store.features.resume.ui.view

import br.com.digital.store.components.strings.StringsUtils.DELIVERY
import br.com.digital.store.components.strings.StringsUtils.DETAILS
import br.com.digital.store.components.strings.StringsUtils.PICKUPS
import br.com.digital.store.components.strings.StringsUtils.RESERVATIONS
import br.com.digital.store.features.order.utils.OrderUtils.SHOPPING_CART

enum class ItemsResume(val text: String) {
    Details(text = DETAILS),
    ResumeShoppingCat(text = SHOPPING_CART),
    ResumeDelivery(text = DELIVERY),
    ResumePickup(text = PICKUPS),
    ResumeReservation(text = RESERVATIONS)
}
