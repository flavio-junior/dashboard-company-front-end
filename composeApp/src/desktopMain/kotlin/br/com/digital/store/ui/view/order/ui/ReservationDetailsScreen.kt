package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.REMOVE_ITEM
import br.com.digital.store.components.strings.StringsUtils.RESERVATIONS
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.reservation.data.vo.ReservationResponseVO
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ReservationDetailsScreen(
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
        orderResponseVO.reservations?.let {
            ListReservations(
                orderId = orderResponseVO.id,
                reservations = it,
                onRefresh = onRefresh
            )
        }
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
    }
}

@Composable
private fun ListReservations(
    orderId: Long,
    modifier: Modifier = Modifier,
    reservations: List<ReservationResponseVO>,
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        Description(description = "$RESERVATIONS:")
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var selectedIndex by remember { mutableStateOf(value = -1) }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            state = scrollState,
            modifier = Modifier
                .background(color = Themes.colors.background)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    },
                )
                .padding(horizontal = Themes.size.spaceSize16)
        ) {
            itemsIndexed(items = reservations) { index, objectResult ->
                CardReservation(
                    orderId = orderId,
                    reservation = objectResult,
                    selected = selectedIndex == index,
                    onDisableItem = {
                        selectedIndex = index
                    },
                    onRefresh = onRefresh
                )
            }
        }
    }
}

@Composable
private fun CardReservation(
    orderId: Long,
    reservation: ReservationResponseVO,
    selected: Boolean = false,
    onDisableItem: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    val viewModel: OrderViewModel = getKoin().get()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    openDialog = true
                    onDisableItem()
                },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = if (selected) ITEM_SELECTED else Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .height(height = Themes.size.spaceSize40)
            .width(width = Themes.size.spaceSize100),
        verticalArrangement = Arrangement.Center
    ) {
        SimpleText(
            text = reservation.name ?: EMPTY_TEXT,
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
    }
    if (openDialog) {
        Alert(
            label = REMOVE_ITEM,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                openDialog = false
                viewModel.removeReservationOrder(
                    orderId = orderId,
                    reservationId = reservation.id ?: 0
                )
            }
        )
    }
    ObserveNetworkStateHandlerRemoveReservationOrder(
        viewModel = viewModel,
        onError = {},
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = onRefresh
    )
}

@Composable
private fun ObserveNetworkStateHandlerRemoveReservationOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.removeReservationOrder }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it.orEmpty()))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.REMOVE_RESERVATION)
            onSuccessful()
        }
    )
}
