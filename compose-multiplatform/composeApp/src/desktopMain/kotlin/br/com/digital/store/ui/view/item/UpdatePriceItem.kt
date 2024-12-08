package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.MESSAGE_ZERO_DOUBLE
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Price
import br.com.digital.store.features.item.data.dto.UpdatePriceItemRequestDTO
import br.com.digital.store.features.item.utils.ItemsUtils.checkPriceIsEqualsZero
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.item.ItemUtils.NEW_PRICE_ITEM
import br.com.digital.store.ui.view.item.ItemUtils.UPDATE_PRICE_ITEM
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.ZERO_DOUBLE
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdatePriceItem(
    modifier: Modifier = Modifier,
    id: Long,
    cleanText: Boolean = false,
    onCleanText: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
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
        var isCleanText by remember { mutableStateOf(value = false) }
        var updatePrice by remember { mutableStateOf(value = ZERO_DOUBLE) }
        if (cleanText) {
            updatePrice = ZERO_DOUBLE
            isCleanText = true
            onCleanText()
        }
        val updatePriceItem = {
            if (checkPriceIsEqualsZero(price = updatePrice.toDouble())) {
                observer = Triple(first = false, second = true, third = MESSAGE_ZERO_DOUBLE)
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
        Price(
            value = updatePrice,
            label = NEW_PRICE_ITEM,
            onValueChange = {
                updatePrice = it
            },
            cleanText = isCleanText,
            isError = observer.second,
            message = observer.third,
            onCleanText = {
                isCleanText = it
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
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                observer = Triple(first = false, second = false, third = MESSAGE_ZERO_DOUBLE)
                updatePrice = ZERO_DOUBLE
                isCleanText = true
                onSuccessful()
            }
        )
        if (openDialog) {
            Alert(
                label = "$UPDATE_PRICE_ITEM?",
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
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updatePriceItem }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onSuccessful()
            viewModel.findAllItems()
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
}
