package br.com.digital.store.features.order.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.CANCEL_ORDER
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DeleteOrder(
    orderId: Long,
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    val viewModel: OrderViewModel = getKoin().get()
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    LoadingButton(
        background = Themes.colors.error,
        label = CANCEL_ORDER,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = "$CANCEL_ORDER?",
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.deleteOrder(id = orderId)
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerDeleteOrder(
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
private fun ObserveNetworkStateHandlerDeleteOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteOrder }
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
            viewModel.resetOrder(reset = ResetOrder.DELETE_ORDER)
            onSuccessful()
        }
    )
}
