package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.item.ItemUtils.DELETE_ITEM
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

@Composable
fun DeleteItem(
    modifier: Modifier = Modifier,
    viewModel: ItemViewModel,
    id: Long,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var openDialog by remember { mutableStateOf(false) }
        LoadingButton(
            label = DELETE_ITEM,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        ObserveNetworkStateHandlerEditItem(
            viewModel = viewModel,
            onError = {
                onError(it)
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                onSuccessful()
            }
        )
        if (openDialog) {
            Alert(
                label = "$DELETE_ITEM?",
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.deleteItem(id = id)
                    openDialog = false
                }
            )
        }
    }
}

@Composable
private fun ObserveNetworkStateHandlerEditItem(
    viewModel: ItemViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteItem }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccessful()
            viewModel.findAllItems()
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
}
