package br.com.digital.store.features.order.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.PENDING_DELIVERY
import br.com.digital.store.components.strings.StringsUtils.SENDING
import br.com.digital.store.components.strings.StringsUtils.STATUS_ORDER
import br.com.digital.store.components.strings.StringsUtils.UPDATE
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.dto.UpdateStatusDeliveryRequestDTO
import br.com.digital.store.features.order.domain.status.AddressStatus
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.UPDATE_STATUS_DELIVERY
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.deliveryStatus
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateStatusDelivery(
    orderId: Long,
    modifier: Modifier = Modifier,
    status: String,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var itemSelected: String by remember {
        mutableStateOf(value = status)
    }
    DropdownMenu(
        selectedValue = itemSelected,
        items = deliveryStatus,
        label = STATUS_ORDER,
        onValueChangedEvent = {
            itemSelected = it
        }
    )
    LoadingButton(
        label = UPDATE,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first,
        modifier = modifier
    )

    if (openDialog) {
        Alert(
            label = UPDATE_STATUS_DELIVERY,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.updateStatusDelivery(
                    orderId = orderId,
                    status = UpdateStatusDeliveryRequestDTO(
                        status = when (itemSelected) {
                            PENDING_DELIVERY -> AddressStatus.PENDING_DELIVERY
                            SENDING -> AddressStatus.SENDING
                            else -> AddressStatus.DELIVERED
                        }
                    )
                )
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerUpdateStatusObject(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            onRefresh()
        }
    )
    onError(observer)
}

@Composable
private fun ObserveNetworkStateHandlerUpdateStatusObject(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateStatusDelivery }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.UPDATE_STATUS_DELIVERY)
            onSuccessful()
        }
    )
}
