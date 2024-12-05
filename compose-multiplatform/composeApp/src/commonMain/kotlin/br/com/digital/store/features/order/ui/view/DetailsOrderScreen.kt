package br.com.digital.store.features.order.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.OrderResponseVO

@Composable
fun DetailsOrderScreen(
    orderResponseVO: OrderResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {

}
