package br.com.digital.store.features.networking.resources

import br.com.digital.store.features.account.di.accountModule
import br.com.digital.store.features.category.di.categoryModule
import br.com.digital.store.features.food.di.foodModule
import br.com.digital.store.features.item.di.itemModule
import br.com.digital.store.features.networking.di.networkModule
import br.com.digital.store.features.order.di.orderModule
import br.com.digital.store.features.product.di.productModule
import br.com.digital.store.features.reservation.di.reservationModule

object NetworkingUtils {
    val COMMON_MODULES = listOf(
        accountModule,
        categoryModule,
        foodModule,
        itemModule,
        networkModule,
        orderModule,
        reservationModule,
        productModule
    )
}
