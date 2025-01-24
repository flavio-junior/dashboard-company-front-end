package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import br.com.digital.store.components.strings.StringsUtils.ADD_RESERVATION
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
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.type.TypeItem
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.pdv.utils.PdvUtils.SELECT_RESERVATIONS
import br.com.digital.store.features.pdv.utils.PdvUtils.SELECT_RESERVATIONS_TO_ORDER
import br.com.digital.store.features.product.ui.view.SelectProducts
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import br.com.digital.store.features.reservation.ui.ui.SelectReservations
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.order.ui.CardObjectSelect
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CreateReservationOrderScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: OrderViewModel = getKoin().get()
    val reservationsToSave = remember { mutableStateListOf<ReservationResponseDTO>() }
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    var addReservations: Boolean by remember { mutableStateOf(value = false) }
    var addProduct: Boolean by remember { mutableStateOf(value = false) }
    var addFood: Boolean by remember { mutableStateOf(value = false) }
    var addItem: Boolean by remember { mutableStateOf(value = false) }
    var verifyObjects: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = SELECT_RESERVATIONS_TO_ORDER)
        LoadingButton(
            label = ADD_RESERVATION,
            onClick = {
                addReservations = true
            },
        )
        ListReservationsSelected(
            reservations = reservationsToSave,
            onItemSelected = { result ->
                if (reservationsToSave.contains(element = result)) {
                    reservationsToSave.remove(element = result)
                }
            }
        )
        Description(description = SELECT_RESERVATIONS)
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
        Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
            objectsToSave.forEach { objectResult ->
                val quantity = objectsToSave.find { it.name == objectResult.name }?.quantity
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
        LoadingButton(
            label = CREATE_NEW_ORDER,
            onClick = {
                if (checkBodyReservationOrderIsNull(
                        reservations = reservationsToSave,
                        objectSelected = objectsToSave
                    )
                ) {
                    observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                } else if (objectsToSave.all { it.quantity == 0 }) {
                    verifyObjects = true
                } else {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.createOrder(
                        order = OrderRequestDTO(
                            type = TypeOrder.RESERVATION,
                            reservations = reservationsToSave.toList(),
                            objects = objectsToSave.toList()
                        )
                    )
                }
            },
            isEnabled = observer.first
        )
        IsErrorMessage(isError = observer.second, observer.third)
        if (addReservations) {
            SelectReservations(
                onDismissRequest = {
                    addReservations = false
                },
                onConfirmation = { reservationsResult ->
                    reservationsResult.forEach { reservation ->
                        reservationsToSave.add(reservation)
                        verifyObjects = false
                    }
                    addReservations = false
                }
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
                        val foodSelected = ObjectRequestDTO(
                            name = food.name,
                            identifier = food.id,
                            quantity = 0,
                            type = TypeItem.FOOD
                        )
                        if (!objectsToSave.contains(element = foodSelected)) {
                            objectsToSave.add(element = foodSelected)
                        }
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
                        val itemSelected = ObjectRequestDTO(
                            name = item.name,
                            identifier = item.id,
                            quantity = 0,
                            type = TypeItem.ITEM
                        )
                        if (!objectsToSave.contains(element = itemSelected)) {
                            objectsToSave.add(element = itemSelected)
                        }
                        verifyObjects = false
                    }
                    addItem = false
                }
            )
        }
        ObserveNetworkStateHandlerCreateReservationOrder(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                objectsToSave.clear()
                reservationsToSave.clear()
                onRefresh()
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerCreateReservationOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<OrderResponseVO> by remember { viewModel.createOrder }
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
            viewModel.resetOrder(reset = ResetOrder.CREATE_ORDER)
            onSuccessful()
        }
    )
}

fun checkBodyReservationOrderIsNull(
    reservations: List<ReservationResponseDTO>? = null,
    objectSelected: List<ObjectRequestDTO>? = null
): Boolean {
    return (reservations.isNullOrEmpty() || objectSelected.isNullOrEmpty())
}
