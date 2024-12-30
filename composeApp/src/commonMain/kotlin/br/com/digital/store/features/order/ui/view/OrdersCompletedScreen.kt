package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.components.strings.StringsUtils.DELIVERY
import br.com.digital.store.components.strings.StringsUtils.ORDERS
import br.com.digital.store.components.strings.StringsUtils.RESERVATIONS
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.data.vo.OrdersResponseVO
import br.com.digital.store.features.order.ui.viewmodel.ClosedOrdersViewModel
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_3
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_9
import br.com.digital.store.utils.onBorder
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun OrdersCompletedScreen(
    onItemSelected: (OrderResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {}
) {
    val viewModel: ClosedOrdersViewModel = getKoin().get()
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllClosedOrders()
    }
    Column(modifier = Modifier.padding(all = Themes.size.spaceSize16)) {
        HeaderOrdersCompletedScreen(modifier = Modifier.weight(weight = WEIGHT_SIZE))
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize16))
        ObserveNetworkStateHandlerPendingOrders(
            viewModel = viewModel,
            onItemSelected = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerPendingOrders(
    viewModel: ClosedOrdersViewModel,
    onItemSelected: (OrderResponseVO) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<OrdersResponseVO> by remember { viewModel.findAllClosedOrders }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            it.result?.let { response ->
                TabsOrdersCompletedScreen(
                    modifier = Modifier,
                    ordersResponseVO = response,
                    onItemSelected = onItemSelected
                )
            } ?: viewModel.showEmptyList(show = true)
        }
    )
}

@Composable
fun HeaderOrdersCompletedScreen(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .onBorder(
                onClick = {},
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Title(
            title = DELIVERY,
            modifier = Modifier
                .padding(all = Themes.size.spaceSize16)
                .weight(weight = WEIGHT_SIZE_3),
            textAlign = TextAlign.Center
        )
        Title(
            title = RESERVATIONS,
            modifier = Modifier
                .weight(weight = WEIGHT_SIZE_4),
            textAlign = TextAlign.Center
        )
        Title(
            title = ORDERS,
            modifier = Modifier
                .weight(weight = WEIGHT_SIZE_3),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TabsOrdersCompletedScreen(
    modifier: Modifier = Modifier,
    ordersResponseVO: OrdersResponseVO,
    onItemSelected: (OrderResponseVO) -> Unit = {}
) {
    Column {
        Row(modifier = modifier.weight(weight = WEIGHT_SIZE_9)) {
            Column(
                modifier = Modifier
                    .background(color = Themes.colors.error)
                    .fillMaxHeight()
                    .weight(weight = WEIGHT_SIZE_3)
            ) {
                Description(description = ordersResponseVO.toString())
            }
            Column(
                modifier = Modifier
                    .background(color = Themes.colors.success)
                    .fillMaxHeight()
                    .weight(weight = WEIGHT_SIZE_4)
            ) {

            }
            Column(
                modifier = Modifier
                    .background(color = Themes.colors.secondary)
                    .fillMaxHeight()
                    .weight(weight = WEIGHT_SIZE_3)
            ) {

            }
        }
    }
}
