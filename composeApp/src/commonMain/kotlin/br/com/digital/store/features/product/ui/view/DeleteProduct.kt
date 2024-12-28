package br.com.digital.store.features.product.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.features.product.ui.viewmodel.ProductViewModel
import br.com.digital.store.features.product.utils.ProductUtils.DELETE_PRODUCT
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DeleteProduct(
    modifier: Modifier = Modifier,
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit
) {
    val viewModel: ProductViewModel = getKoin().get()
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    LoadingButton(
        onClick = {
            openDialog = true
        },
        label = DELETE_PRODUCT,
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = "$DELETE_PRODUCT?",
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.deleteProduct(id = id)
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerUpdatePriceProduct(
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
private fun ObserveNetworkStateHandlerUpdatePriceProduct(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteProduct }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllProducts()
            onSuccessful()
        }
    )
}
