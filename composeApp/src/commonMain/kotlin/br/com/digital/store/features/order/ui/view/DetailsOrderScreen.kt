package br.com.digital.store.features.order.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.components.ui.ResourceUnavailable
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun DetailsOrderScreen(
    orderResponseVO: OrderResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    if (orderResponseVO.id > NUMBER_ZERO) {
        when (orderResponseVO.type) {
            TypeOrder.DELIVERY -> DeliveryDetailsScreen(
                orderResponseVO = orderResponseVO,
                onItemSelected = onItemSelected,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )

            TypeOrder.ORDER -> OrderDetailsScreen(
                orderResponseVO = orderResponseVO,
                onItemSelected = onItemSelected,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )

            TypeOrder.RESERVATION -> ReservationDetailsScreen(
                orderResponseVO = orderResponseVO,
                onItemSelected = onItemSelected,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )

            else -> {}
        }
    } else {
        ResourceUnavailable()
    }
}
