package br.com.digital.store.features.resume.ui.view

import br.com.digital.store.components.strings.StringsUtils.DELIVERY
import br.com.digital.store.components.strings.StringsUtils.PICKUPS
import br.com.digital.store.components.strings.StringsUtils.RESERVATIONS
import br.com.digital.store.features.order.utils.OrderUtils.SHOPPING_CART
import br.com.digital.store.features.resume.utils.ResumeUtils.RESUME_DAY

enum class ItemsResume(val text: String) {
    ResumeDay(text = RESUME_DAY),
    ResumeShoppingCat(text = SHOPPING_CART),
    ResumeDelivery(text = DELIVERY),
    ResumePickup(text = PICKUPS),
    ResumeReservation(text = RESERVATIONS)
}
