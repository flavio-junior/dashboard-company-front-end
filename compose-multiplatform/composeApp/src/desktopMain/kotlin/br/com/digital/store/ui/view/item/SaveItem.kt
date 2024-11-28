package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.strings.StringsUtils.PRICE
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.item.ItemUtils.ITEM_NAME
import br.com.digital.store.ui.view.item.ItemUtils.SAVE_ITEM
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.checkNameIsNull
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SaveItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val viewModel: ItemViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var name by remember { mutableStateOf(value = EMPTY_TEXT) }
        var price by remember { mutableStateOf(value = EMPTY_TEXT) }
        val saveItem = {
            if (checkNameIsNull(name = name)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
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
            message = observer.third,
            onValueChange = { name = it },
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            onGo = {
                saveItem()
            }
        )
        TextField(
            label = PRICE,
            value = price,
            icon = Res.drawable.edit,
            keyboardType = KeyboardType.Decimal,
            isError = observer.second,
            message = observer.third,
            onValueChange = { price = it },
            modifier = modifier.weight(weight = WEIGHT_SIZE),
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
            modifier = modifier.weight(weight = WEIGHT_SIZE)
        )
        ObserveNetworkStateHandlerCreateNewItem(
            viewModel = viewModel,
            onError = {
                observer = it
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewItem(
    viewModel: ItemViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewItem }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllItems()
        }
    )
}
