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
    const val ERROR_TIMEOUT = "Conexão Perdida com o Servidor!"
    const val ERROR_UNKNOWN = "Error desconhecido"
    const val TIMEOUT_SIZE = 60000L
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
