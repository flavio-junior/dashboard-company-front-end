package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.ResourceUnavailable
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun AddMoreItems(
    orderResponseVO: OrderResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    if (orderResponseVO.id > NUMBER_ZERO) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            HeaderDetailsOrder(orderResponseVO = orderResponseVO)
            IncrementMoreObjectsOrderScreen(
                orderId = orderResponseVO.id,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = {
                    onRefresh()
                }
            )
            Spacer(modifier = Modifier.height(height = Themes.size.spaceSize64))
        }
    } else {
        ResourceUnavailable(modifier = Modifier.fillMaxSize())
    }
}
