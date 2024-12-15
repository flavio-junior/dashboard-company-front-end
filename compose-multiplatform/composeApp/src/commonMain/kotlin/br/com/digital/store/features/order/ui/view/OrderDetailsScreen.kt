package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.theme.Themes

@Composable
fun OrderDetailsScreen(
    orderResponseVO: OrderResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        HeaderDetailsOrder(orderResponseVO = orderResponseVO)
        orderResponseVO.objects?.let {
            Object(
                orderResponseVO = orderResponseVO,
                objects = it,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )
        }
    }
}
