package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_FOOD
import br.com.digital.store.components.strings.StringsUtils.ADD_ITEM
import br.com.digital.store.components.strings.StringsUtils.ADD_PRODUCT
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.features.product.ui.view.SelectProducts
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE

@Composable
fun AddItemsOrder() {
    Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
        var addProduct:Boolean by remember { mutableStateOf(value = false) }
        var addFood:Boolean by remember { mutableStateOf(value = false) }
        var addItem:Boolean by remember { mutableStateOf(value = false) }
        LoadingButton(
            label = ADD_PRODUCT,
            onClick = {
                addProduct = true
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            label = ADD_FOOD,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            label = ADD_ITEM,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )

        if (addProduct) {
            SelectProducts(
                onDismissRequest = {
                    addProduct = false
                },
                onConfirmation = {
                    //selectedCategories.addAll(it)
                    addProduct = false
                }
            )
        }
    }
}
