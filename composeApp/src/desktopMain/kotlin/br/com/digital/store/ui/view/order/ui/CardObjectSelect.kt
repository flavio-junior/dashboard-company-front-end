package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.strings.StringsUtils.REMOVER_RESERVATION
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.domain.factory.typeOrderFactory
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.NUMBER_EQUALS_ZERO
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.store.utils.onBorder

@Composable
fun CardObjectSelect(
    objectRequestDTO: ObjectRequestDTO,
    quantity: Int = NUMBER_ZERO,
    selected: Boolean = false,
    verifyObject: Boolean = false,
    onItemSelected: (ObjectRequestDTO) -> Unit = {},
    onQuantityChange: (Int) -> Unit = {},
    onResult: (ObjectRequestDTO) -> Unit = {},
    onDisableItem: () -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var newQuantity: Int by remember { mutableStateOf(value = quantity) }
    var observer: Pair<Boolean, String> by remember {
        mutableStateOf(value = Pair(first = false, second = EMPTY_TEXT))
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    openDialog = true
                    onDisableItem()
                },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = if (selected) ITEM_SELECTED else Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .width(width = Themes.size.spaceSize200)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Title(
            title = typeOrderFactory(objectRequestDTO.type),
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        TextField(
            label = NAME,
            value = objectRequestDTO.name,
            backgroundColor = if (selected) ITEM_SELECTED else Themes.colors.background,
            textColor = if (selected) Themes.colors.background else Themes.colors.primary,
            enabled = false,
            isError = observer.first,
            keyboardType = KeyboardType.Number,
            onValueChange = {}
        )
        TextField(
            label = QUANTITY,
            value = newQuantity.toString(),
            backgroundColor = if (selected) ITEM_SELECTED else Themes.colors.background,
            textColor = if (selected) Themes.colors.background else Themes.colors.primary,
            isError = observer.first,
            message = observer.second,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                newQuantity = it.toIntOrNull() ?: NUMBER_ZERO
            }
        )
        if (newQuantity > NUMBER_ZERO) {
            onQuantityChange(newQuantity)
        }
        if (verifyObject) {
            if (quantity > NUMBER_ZERO) {
                observer = Pair(first = false, second = EMPTY_TEXT)
                onResult(
                    ObjectRequestDTO(
                        name = objectRequestDTO.name,
                        identifier = objectRequestDTO.identifier,
                        quantity = quantity,
                        type = objectRequestDTO.type
                    )
                )
            } else {
                observer = Pair(first = true, second = NUMBER_EQUALS_ZERO)
            }
        }
        if (openDialog) {
            Alert(
                label = REMOVER_RESERVATION,
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    onItemSelected(objectRequestDTO)
                    openDialog = false
                }
            )
        }
    }
}
