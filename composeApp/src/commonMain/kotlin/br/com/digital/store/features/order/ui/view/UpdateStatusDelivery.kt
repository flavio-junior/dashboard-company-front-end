package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.padding
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
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.features.order.data.dto.UpdateStatusDeliveryRequestDTO
import br.com.digital.store.features.order.domain.status.AddressStatus
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.UPDATE_STATUS
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.updateStatusDelivery
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateStatusDelivery(
    modifier: Modifier = Modifier,
    orderId: Long,
    status: String,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var itemSelected: String by remember {
        mutableStateOf(value = status)
    }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: OrderViewModel = getKoin().get()
    ItemObject(
        modifier = Modifier.padding(
            top = Themes.size.spaceSize8,
            end = Themes.size.spaceSize16
        ),
        body = {
            DropdownMenu(
                selectedValue = itemSelected,
                items = updateStatusDelivery,
                label = STATUS_ORDER,
                onValueChangedEvent = {
                    itemSelected = it
                },
                modifier = modifier
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
                    label = UPDATE_STATUS,
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
        }
    )
    ObserveNetworkStateHandlerUpdateStatusDelivery(
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
}

@Composable
private fun ObserveNetworkStateHandlerUpdateStatusDelivery(
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
