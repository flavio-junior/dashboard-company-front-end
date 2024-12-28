package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.strings.StringsUtils.MESSAGE_ZERO_DOUBLE
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Price
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.item.ui.viewmodel.ItemViewModel
import br.com.digital.store.features.item.ui.viewmodel.ResetItem
import br.com.digital.store.features.item.utils.checkBodyItemIsNull
import br.com.digital.store.features.item.utils.checkPriceIsEqualsZero
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.item.ItemUtils.ITEM_NAME
import br.com.digital.store.ui.view.item.ItemUtils.SAVE_ITEM
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.CommonUtils.ZERO_DOUBLE
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SaveItem(
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = modifier.padding(top = Themes.size.spaceSize8),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val viewModel: ItemViewModel = getKoin().get()
            var name by remember { mutableStateOf(value = EMPTY_TEXT) }
            var price by remember { mutableStateOf(value = ZERO_DOUBLE) }
            var cleanText by remember { mutableStateOf(value = false) }
            val saveItem = {
                if (checkBodyItemIsNull(name = name, price = price.toDouble())) {
                    observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                } else if (checkPriceIsEqualsZero(price = price.toDouble())) {
                    observer = Triple(first = false, second = true, third = MESSAGE_ZERO_DOUBLE)
                } else {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.createItem(name = name, price = price.toDouble())
                }
            }
            TextField(
                label = ITEM_NAME,
                value = name,
                icon = Res.drawable.edit,
                keyboardType = KeyboardType.Text,
                isError = observer.second,
                onValueChange = { name = it },
                modifier = modifier.weight(weight = WEIGHT_SIZE_2),
                onGo = {
                    saveItem()
                }
            )
            Price(
                value = price,
                onValueChange = {
                    price = it
                },
                modifier = modifier.weight(weight = WEIGHT_SIZE_2),
                cleanText = cleanText,
                isError = observer.second,
                onCleanText = {
                    cleanText = it
                },
                onGo = {
                    saveItem()
                }
            )
            LoadingButton(
                label = SAVE_ITEM,
                onClick = {
                    saveItem()
                },
                isEnabled = observer.first,
                modifier = modifier.weight(weight = WEIGHT_SIZE_2)
            )
            ObserveNetworkStateHandlerCreateNewItem(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    name = EMPTY_TEXT
                    price = ZERO_DOUBLE
                    cleanText = true
                }
            )
        }
        IsErrorMessage(isError = observer.second, message = observer.third)
    }
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewItem(
    viewModel: ItemViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewItem }
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
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetItem(reset = ResetItem.CREATE_ITEM)
            onSuccessful()
        }
    )
}
