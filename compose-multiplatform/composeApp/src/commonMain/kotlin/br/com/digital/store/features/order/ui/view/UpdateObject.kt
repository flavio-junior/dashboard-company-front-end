package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_ITEM
import br.com.digital.store.components.strings.StringsUtils.QTD
import br.com.digital.store.components.strings.StringsUtils.REMOVER_ITEM
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.order.utils.OrderUtils.DECREMENT_ITEM
import br.com.digital.store.features.order.utils.OrderUtils.INCREMENT_ITEM
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun UpdateObject(
    orderId: Long,
    objectId: Long
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
        ItemObject(
            body = {
                incrementMoreDataObject(
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    orderId = orderId,
                    objectId = objectId
                )
            }
        )
        ItemObject(
            body = {
                decrementMoreDataObject(
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    orderId = orderId,
                    objectId = objectId
                )
            }
        )
    }
}

@Composable
private fun incrementMoreDataObject(
    modifier: Modifier = Modifier,
    orderId: Long,
    objectId: Long
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    TextField(
        label = QTD,
        value = quantity.toString(),
        onValueChange = {
            quantity = it.toIntOrNull() ?: NUMBER_ZERO
        },
        modifier = modifier
    )
    LoadingButton(
        label = ADD_ITEM,
        onClick = {
            openDialog = true
        },
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = INCREMENT_ITEM,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                openDialog = false
            }
        )
    }
}

@Composable
private fun decrementMoreDataObject(
    modifier: Modifier = Modifier,
    orderId: Long,
    objectId: Long
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    TextField(
        label = QTD,
        value = quantity.toString(),
        onValueChange = {
            quantity = it.toIntOrNull() ?: NUMBER_ZERO
        },
        modifier = modifier
    )
    LoadingButton(
        label = REMOVER_ITEM,
        onClick = {
            openDialog = true
        },
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = DECREMENT_ITEM,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                openDialog = false
            }
        )
    }
}
