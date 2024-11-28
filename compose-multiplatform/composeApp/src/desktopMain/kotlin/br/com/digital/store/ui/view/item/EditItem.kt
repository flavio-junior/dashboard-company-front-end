package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.features.item.data.dto.EditItemRequestDTO
import br.com.digital.store.features.item.data.vo.ItemResponseVO
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.close
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.components.strings.StringsUtils.ACTUAL_NAME
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.item.ItemUtils.EDIT_ITEM
import br.com.digital.store.ui.view.item.ItemUtils.NEW_NAME_ITEM
import br.com.digital.store.ui.view.item.ItemUtils.NEW_PRICE_ITEM
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_3
import br.com.digital.store.utils.checkNameIsNull
import br.com.digital.store.utils.onBorder
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun EditItem(
    modifier: Modifier = Modifier,
    itemVO: ItemResponseVO,
    onCleanItem: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        val viewModel: ItemViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var openDialog by remember { mutableStateOf(value = false) }
        var newName by remember { mutableStateOf(value = EMPTY_TEXT) }
        var newPrice by remember { mutableStateOf(value = EMPTY_TEXT) }
        var cleanText:Boolean by remember { mutableStateOf(value = false) }
        val editItem = { item: String ->
            if (checkNameIsNull(name = item)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.editItem(
                    item = EditItemRequestDTO(
                        id = itemVO.id,
                        name = item,
                        price = newPrice.toDouble()
                    )
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = itemVO.id.toString(),
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
            )
            TextField(
                enabled = false,
                label = ACTUAL_NAME,
                value = itemVO.name,
                modifier = Modifier.weight(weight = WEIGHT_SIZE_3),
            )
            IconDefault(
                icon = Res.drawable.close, modifier = Modifier
                    .onBorder(
                        onClick = {},
                        color = Themes.colors.primary,
                        spaceSize = Themes.size.spaceSize16,
                        width = Themes.size.spaceSize2
                    )
                    .size(size = Themes.size.spaceSize64)
                    .padding(all = Themes.size.spaceSize8),
                onClick = {
                    onCleanItem()
                    cleanText = true
                }
            )
        }
        TextField(
            label = NEW_NAME_ITEM,
            value = newName,
            icon = Res.drawable.edit,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                newName = it
            },
            onGo = {
                openDialog = true
            }
        )
        TextField(
            label = NEW_PRICE_ITEM,
            value = newPrice,
            icon = Res.drawable.edit,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                newPrice = it
            },
            onGo = {
                openDialog = true
            }
        )
        LoadingButton(
            label = EDIT_ITEM,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        if (openDialog) {
            Alert(
                label = EDIT_ITEM,
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    editItem(newName)
                    openDialog = false
                }
            )
        }
        ObserveNetworkStateHandlerEditItem(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            onSuccessful = {
                onCleanItem()
                newName = EMPTY_TEXT
                newPrice = EMPTY_TEXT
            }
        )
        UpdatePriceItem(
            id = itemVO.id,
            modifier = Modifier.padding(top = Themes.size.spaceSize16),
            cleanText = cleanText,
            onCleanText = {
                cleanText = false
            },
            onError = {
                observer = it
            },
            onSuccessful = {
                onCleanItem()
            }
        )
        DeleteItem(
            viewModel = viewModel,
            id = itemVO.id,
            modifier = Modifier.padding(top = Themes.size.spaceSize16),
            onError = {
                observer = it
            },
            onSuccessful = {
                onCleanItem()
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerEditItem(
    viewModel: ItemViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.editItem }
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
