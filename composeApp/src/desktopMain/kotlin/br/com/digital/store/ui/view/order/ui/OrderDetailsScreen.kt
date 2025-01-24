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
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.theme.Themes

@Composable
fun OrderDetailsScreen(
    orderResponseVO: OrderResponseVO,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {},
    objectSelected: (Pair<Long, ObjectResponseVO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .verticalScroll(state = rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        HeaderDetailsOrder(orderResponseVO = orderResponseVO)
        orderResponseVO.objects?.let {
            Object(
                orderResponseVO = orderResponseVO,
                objects = it,
                onItemSelected = onItemSelected,
                objectSelected = objectSelected,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )
        }
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize64))
    }
}
