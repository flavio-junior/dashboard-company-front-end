package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.common.item.dto.UpdatePriceItemRequestDTO
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.item.ItemUtils.NEW_PRICE_ITEM
import br.com.digital.store.ui.view.item.ItemUtils.UPDATE_PRICE_ITEM
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.checkNameIsNull
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdatePriceItem(
    modifier: Modifier = Modifier,
    id: Long,
    cleanText: Boolean = false,
    onCleanText: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        val viewModel: ItemViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var openDialog by remember { mutableStateOf(false) }
        var updatePrice by remember { mutableStateOf(value = EMPTY_TEXT) }
        if (cleanText) {
            updatePrice = EMPTY_TEXT
            onCleanText()
        }
        val updatePriceItem = {
            if (checkNameIsNull(name = updatePrice)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.updatePriceItem(
                    id = id,
                    item = UpdatePriceItemRequestDTO(
                        id = id,
                        price = updatePrice.toDouble()
                    )
                )
            }
        }
        TextField(
            label = NEW_PRICE_ITEM,
            value = updatePrice,
            icon = Res.drawable.edit,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                updatePrice = it
            },
            onGo = {
                openDialog = true
            }
        )
        LoadingButton(
            label = UPDATE_PRICE_ITEM,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        ObserveNetworkStateHandlerUpdatePriceItem(
            viewModel = viewModel,
            onError = {
                onError(it)
                observer = it
            },
            onSuccessful = {
                observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                updatePrice = EMPTY_TEXT
                onSuccessful()
            }
        )
        if (openDialog) {
            Alert(
                label = UPDATE_PRICE_ITEM,
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    updatePriceItem()
                    openDialog = false
                }
            )
        }
    }
}

@Composable
private fun ObserveNetworkStateHandlerUpdatePriceItem(
    viewModel: ItemViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updatePriceItem }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        onSuccess = {
            onSuccessful()
            viewModel.findAllItems()
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
}
