package br.com.digital.store.ui.view.order.ui

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.utils.NumbersUtils

@Composable
fun OrderTabMain(
    index: Int,
    orderResponseVO: OrderResponseVO = OrderResponseVO(),
    objectResponseVO: Pair<Long, ObjectResponseVO>,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {},
    objectResult: (Pair<Long, ObjectResponseVO>) -> Unit = {},
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
            objectSelected = objectResult,
            onItemSelected = onItemSelected,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_ONE)
            }
        )

        NumbersUtils.NUMBER_TWO -> DetailsItem(
            orderId = objectResponseVO.first,
            objectResponseVO = objectResponseVO.second,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_TWO)
            }
        )

        NumbersUtils.NUMBER_THREE -> AlterAddress(
            orderResponseVO = orderResponseVO,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_THREE)
            }
        )

        NumbersUtils.NUMBER_FOUR ->
            AddMoreItems(
                orderResponseVO = orderResponseVO,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = {
                    onRefresh(NumbersUtils.NUMBER_FOUR)
                }
            )

        NumbersUtils.NUMBER_FIVE ->
            AddMoreReservationsScreen(
                orderId = orderResponseVO.id,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = {
                    onRefresh(NumbersUtils.NUMBER_FIVE)
                }
            )
    }
}
