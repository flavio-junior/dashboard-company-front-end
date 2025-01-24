package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_FOOD
import br.com.digital.store.components.strings.StringsUtils.ADD_ITEM
import br.com.digital.store.components.strings.StringsUtils.ADD_PRODUCT
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.features.food.ui.view.SelectFoods
import br.com.digital.store.features.item.ui.view.SelectItems
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.domain.type.TypeItem
import br.com.digital.store.features.product.ui.view.SelectProducts
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE

@Composable
fun SelectObjects(
    onItemSelected: Pair<Boolean, ObjectRequestDTO>,
    objectsSelected: (List<ObjectRequestDTO>) -> Unit = {}
) {
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    var addProduct: Boolean by remember { mutableStateOf(value = false) }
    var addFood: Boolean by remember { mutableStateOf(value = false) }
    var addItem: Boolean by remember { mutableStateOf(value = false) }
    Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
        LoadingButton(
            label = ADD_PRODUCT,
            onClick = {
                addProduct = true
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            label = ADD_FOOD,
            onClick = {
                addFood = true
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            label = ADD_ITEM,
            onClick = {
                addItem = true
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
    if (addProduct) {
        SelectProducts(
            onDismissRequest = {
                addProduct = false
            },
            onConfirmation = {
                it.forEach { product ->
                    val productSelected = ObjectRequestDTO(
                        name = product.name,
                        identifier = product.id,
                        actualQuantity = product.quantity,
                        quantity = 0,
                        type = TypeItem.PRODUCT
                    )
                    if (!objectsToSave.contains(element = productSelected)) {
                        objectsToSave.add(productSelected)
                    }
                }
                addProduct = false
            }
        )
    }
    if (addFood) {
        SelectFoods(
            onDismissRequest = {
                addFood = false
            },
            onConfirmation = {
                it.forEach { food ->
                    val foodSelected = ObjectRequestDTO(
                        name = food.name,
                        identifier = food.id,
                        quantity = 0,
                        type = TypeItem.FOOD
                    )
                    if (!objectsToSave.contains(element = foodSelected)) {
                        objectsToSave.add(element = foodSelected)
                    }
                }
                addFood = false
            }
        )
    }
    if (addItem) {
        SelectItems(
            onDismissRequest = {
                addItem = false
            },
            onConfirmation = {
                it.forEach { item ->
                    val itemSelected = ObjectRequestDTO(
                        name = item.name,
                        identifier = item.id,
                        quantity = 0,
                        type = TypeItem.ITEM
                    )
                    if (!objectsToSave.contains(element = itemSelected)) {
                        objectsToSave.add(element = itemSelected)
                    }
                }
                addItem = false
            }
        )
    }
    if (objectsToSave.isNotEmpty()) {
        if (onItemSelected.first && objectsToSave.contains(element = onItemSelected.second)) {
            objectsToSave.remove(element = onItemSelected.second)
        }
        objectsSelected(objectsToSave)
    }
}
