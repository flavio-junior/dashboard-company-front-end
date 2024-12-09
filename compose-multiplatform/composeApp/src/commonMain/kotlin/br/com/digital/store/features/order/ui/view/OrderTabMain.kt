package br.com.digital.store.features.order.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.utils.NumbersUtils

@Composable
fun OrderTabMain(
    index: Int,
    orderResponseVO: OrderResponseVO = OrderResponseVO(),
    onItemSelected: (OrderResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> PendingOrdersScreen(
            onItemSelected = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes
        )

        NumbersUtils.NUMBER_ONE -> DetailsOrderScreen(
            orderResponseVO = orderResponseVO,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_ONE)
            }
        )
    }
}
