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
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.SELECTED_ITEMS
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.food.ui.view.SelectFoods
import br.com.digital.store.features.item.ui.view.SelectItems
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.dto.AddressRequestDTO
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.domain.type.TypeItem
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.ui.view.AllObjects
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.product.ui.view.SelectProducts
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun AddItemsOrder(
    street: String,
    number: Int,
    district: String,
    complement: String,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: OrderViewModel = getKoin().get()
    val objectsSelected = remember { mutableStateListOf<ObjectRequestDTO>() }
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    var addProduct: Boolean by remember { mutableStateOf(value = false) }
    var addFood: Boolean by remember { mutableStateOf(value = false) }
    var addItem: Boolean by remember { mutableStateOf(value = false) }
    var verifyObjects: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
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
    AllObjects(
        objectSelected = objectsSelected,
        verifyObjects = verifyObjects,
        objectsToSave = { objectsResult ->
            objectsResult.forEach { objectResult ->
                val existingItemIndex = objectsToSave.indexOfFirst { it.identifier == objectResult.identifier }
                if (existingItemIndex != -1) {
                    objectsToSave[existingItemIndex] = objectResult
                } else {
                    objectsToSave.add(objectResult)
                }
            }
        }
    )
    LoadingButton(
        label = CREATE_NEW_ORDER,
        onClick = {
            if (checkBodyOrderIsNull(
                    street = street,
                    number = number,
                    district = district,
                    complement = complement,
                    objectSelected = objectsSelected
                )
            ) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else if (objectsToSave.all { it.quantity == 0 }) {
                verifyObjects = true
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.createOrder(
                    order = OrderRequestDTO(
                        type = TypeOrder.DELIVERY,
                        address = AddressRequestDTO(
                            street = street,
                            number = number,
                            district = district,
                            complement = complement
                        ),
                        objects = objectsToSave
                    )
                )
            }
        },
        isEnabled = observer.first
    )
    IsErrorMessage(isError = observer.second, observer.third)
    if (addProduct) {
        SelectProducts(
            onDismissRequest = {
                addProduct = false
            },
            onConfirmation = {
                it.forEach { product ->
                    objectsSelected.add(
                        ObjectRequestDTO(
                            name = product.name,
                            identifier = product.id,
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
                it.forEach { food ->
                    objectsSelected.add(
                        ObjectRequestDTO(
                            name = food.name,
                            identifier = food.id,
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
    if (addItem) {
        SelectItems(
            onDismissRequest = {
                addItem = false
            },
            onConfirmation = {
                it.forEach { item ->
                    objectsSelected.add(
                        ObjectRequestDTO(
                            name = item.name,
                            identifier = item.id,
                            quantity = 0,
                            type = TypeItem.ITEM
                        )
                    )
                    verifyObjects = false
                }
                addItem = false
            }
        )
    }
    ObserveNetworkStateHandlerCreateNewOrder(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            objectsSelected.clear()
            objectsToSave.clear()
            onRefresh()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createOrder }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it.orEmpty()))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccessful()
        }
    )
}

fun checkBodyOrderIsNull(
    street: String,
    number: Int,
    district: String,
    complement: String,
    objectSelected: List<ObjectRequestDTO>? = null
): Boolean {
    return (street.isEmpty() || number == NUMBER_ZERO || district.isEmpty() || complement.isEmpty() || objectSelected.isNullOrEmpty())
}
