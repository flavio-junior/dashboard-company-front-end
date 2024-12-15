package br.com.digital.store.ui.view.pdv

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
import br.com.digital.store.components.strings.StringsUtils.CREATE_NEW_ORDER
import br.com.digital.store.components.strings.StringsUtils.SELECTED_ITEMS
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.features.food.ui.view.SelectFoods
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.domain.type.TypeItem
import br.com.digital.store.features.order.ui.view.CardObjectSelect
import br.com.digital.store.features.order.ui.view.ItemObject
import br.com.digital.store.features.product.ui.view.SelectProducts
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE

@Composable
fun AddItemsOrder() {
    var objectSelected = remember { mutableStateListOf<ObjectRequestDTO>() }
    var addProduct: Boolean by remember { mutableStateOf(value = false) }
    var addFood: Boolean by remember { mutableStateOf(value = false) }
    var addItem: Boolean by remember { mutableStateOf(value = false) }
    var verifyObjects: Boolean by remember { mutableStateOf(value = false) }
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
    Description(description = SELECTED_ITEMS)
    ItemObject(
        body = {
            objectSelected.forEach {
                CardObjectSelect(
                    objectRequestDTO = it,
                    verifyObject = verifyObjects
                )
            }
        }
    )
    LoadingButton(
        label = CREATE_NEW_ORDER,
        onClick = {
            verifyObjects = true
        }
    )
    if (addProduct) {
        SelectProducts(
            onDismissRequest = {
                addProduct = false
            },
            onConfirmation = {
                it.forEach {
                    objectSelected.add(
                        ObjectRequestDTO(
                            name = it.name,
                            identifier = it.id,
                            quantity = 0,
                            type = TypeItem.PRODUCT
                        )
                    )
                    verifyObjects = false
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
                it.forEach {
                    objectSelected.add(
                        ObjectRequestDTO(
                            name = it.name,
                            identifier = it.id,
                            quantity = 0,
                            type = TypeItem.FOOD
                        )
                    )
                    verifyObjects = false
                }
                addFood = false
            }
        )
    }
}
