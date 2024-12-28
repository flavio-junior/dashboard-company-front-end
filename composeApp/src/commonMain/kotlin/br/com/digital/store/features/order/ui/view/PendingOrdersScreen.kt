package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import br.com.digital.store.components.ui.EmptyList
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.data.vo.OrdersResponseVO
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.ui.viewmodel.OpenOrdersViewModel
import br.com.digital.store.features.order.utils.OrderUtils.EMPTY_LIST_ORDERS
import br.com.digital.store.features.product.utils.ProductUtils.CREATE_PRODUCT
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_3
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_9
import br.com.digital.store.utils.onBorder
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun PendingOrdersScreen(
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: OpenOrdersViewModel = getKoin().get()
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllOpenOrders()
    }
    Column(modifier = Modifier.padding(all = Themes.size.spaceSize16)) {
        HeaderPendingOrdersScreen(modifier = Modifier.weight(weight = WEIGHT_SIZE))
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
    viewModel: OpenOrdersViewModel,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<OrdersResponseVO> by remember { viewModel.findAllOpenOrders }
    val showEmptyList: Boolean by remember { viewModel.showEmptyList }
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
            if (showEmptyList) {
                EmptyList(
                    title = EMPTY_LIST_ORDERS,
                    description = "$CREATE_PRODUCT?",
                    onClick = onToCreateNewProduct,
                    refresh = {
                        viewModel.findAllOpenOrders()
                    }
                )
            } else {
                it.result?.let { response ->
                    TabsPendingOrdersScreen(
                        ordersResponseVO = response,
                        onItemSelected = onItemSelected
                    )
                } ?: viewModel.showEmptyList(show = true)
            }
        }
    )
}

@Composable
fun HeaderPendingOrdersScreen(
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
private fun TabsPendingOrdersScreen(
    modifier: Modifier = Modifier,
    ordersResponseVO: OrdersResponseVO,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {}
) {
    val delivery = ordersResponseVO.content?.filter { it.type == TypeOrder.DELIVERY } ?: emptyList()
    val orders = ordersResponseVO.content?.filter { it.type == TypeOrder.ORDER } ?: emptyList()
    val reservations =
        ordersResponseVO.content?.filter { it.type == TypeOrder.RESERVATION } ?: emptyList()
    Column {
        Row(modifier = modifier.weight(weight = WEIGHT_SIZE_9)) {
            DeliveryList(
                modifier = modifier.weight(weight = WEIGHT_SIZE_3),
                orderResponseVO = delivery,
                onItemSelected = onItemSelected
            )
            OrderList(
                modifier = modifier.weight(weight = WEIGHT_SIZE_4),
                orderResponseVO = orders,
                onItemSelected = onItemSelected
            )
            ReservationList(
                modifier = modifier.weight(weight = WEIGHT_SIZE_3),
                orderResponseVO = reservations,
                onItemSelected = onItemSelected
            )
        }
    }
}
