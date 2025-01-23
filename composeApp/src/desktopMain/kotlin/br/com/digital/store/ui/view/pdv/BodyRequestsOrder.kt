package br.com.digital.store.ui.view.pdv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.digital.store.components.strings.StringsUtils.ADD_PRODUCT
import br.com.digital.store.components.ui.ButtonCreate
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.ui.view.order.utils.BodyObject
import br.com.digital.store.ui.view.order.ui.CardObjectSelect
import br.com.digital.store.ui.view.order.ui.ItemObject
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun BodyRequestProduct(
    objectsToSave: MutableList<ObjectRequestDTO>,
    contentObjects: MutableList<BodyObject>,
    verifyObjects: Boolean,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onResult: (List<ObjectRequestDTO>) -> Unit = {}
) {
    var addNewProduct: Boolean by remember { mutableStateOf(value = false) }
    var addNewFood: Boolean by remember { mutableStateOf(value = false) }
    var addNewItem: Boolean by remember { mutableStateOf(value = false) }
    ItemObject(
        body = {
            ButtonCreate(
                label = ADD_PRODUCT,
                onItemSelected = {
                    addNewProduct = true
                }
            )
            objectsToSave.forEach { objectResult ->
                val quantity = contentObjects.find { it.name == objectResult.name }?.quantity
                    ?: NUMBER_ZERO
                CardObjectSelect(
                    objectRequestDTO = objectResult,
                    verifyObject = verifyObjects,
                    quantity = quantity,
                    onQuantityChange = {
                        objectResult.quantity = it
                    },
                    onItemSelected = {
                        if (objectsToSave.contains(element = it)) {
                            objectsToSave.remove(element = it)
                        }
                    }
                )
            }
        }
    )
}
