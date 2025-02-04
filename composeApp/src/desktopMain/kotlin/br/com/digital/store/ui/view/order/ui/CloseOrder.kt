package br.com.digital.store.ui.view.order.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.fee.data.vo.FeeResponseOrderVO
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.CLOSE_ORDER
import br.com.digital.store.ui.view.components.ui.SelectPrint
import br.com.digital.store.ui.view.components.utils.ThermalPrinter
import br.com.digital.store.ui.view.order.utils.formatOrderToPrint
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin
import java.io.ByteArrayInputStream

@Composable
fun CloseOrder(
    orderId: Long,
    fee: FeeResponseOrderVO? = null,
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    val thermalPrinter = ThermalPrinter()
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var openPrint: Boolean by remember { mutableStateOf(value = false) }
    var getOrder by remember { mutableStateOf(value = OrderResponseVO()) }
    LoadingButton(
        label = CLOSE_ORDER,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        ClosedOrderDialog(
            fee = fee,
            force = true,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.closeOrder(orderId = orderId, force = it.second, payment = it.first)
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerCloseOrder(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            openPrint = true
            if (it != null) {
                getOrder = it
            }
        }
    )
    val textToDisplay = formatOrderToPrint(order = getOrder)
    val inputStream = ByteArrayInputStream(textToDisplay.toByteArray(Charsets.UTF_8))
    if (openPrint) {
        SelectPrint(
            onDismissRequest = {
                openPrint = false
                onRefresh()
            },
            onConfirmation = { printerName ->
                openPrint = false
                thermalPrinter.printInputStream(inputStream, printerName)
                onRefresh()
            }
        )
    }
    onError(observer)
}

@Composable
private fun ObserveNetworkStateHandlerCloseOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: (OrderResponseVO?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<OrderResponseVO> by remember { viewModel.closeOrder }
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
            viewModel.resetOrder(reset = ResetOrder.CLOSE_ORDER)
            onSuccessful(it.result)
        }
    )
}
