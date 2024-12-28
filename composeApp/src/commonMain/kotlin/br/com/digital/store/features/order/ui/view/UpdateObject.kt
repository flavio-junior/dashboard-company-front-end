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
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.domain.others.Action
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.DECREMENT_ITEM
import br.com.digital.store.features.order.utils.OrderUtils.INCREMENT_ITEM
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.NUMBER_EQUALS_ZERO
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateObject(
    orderId: Long,
    objectId: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
        ItemObject(
            body = {
                IncrementMoreItemsObject(
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    orderId = orderId,
                    objectId = objectId,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = onRefresh
                )
            }
        )
        ItemObject(
            body = {
                DecrementItemsObject(
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    orderId = orderId,
                    objectId = objectId,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = onRefresh
                )
            }
        )
    }
}

@Composable
private fun IncrementMoreItemsObject(
    modifier: Modifier = Modifier,
    orderId: Long,
    objectId: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val saveIncrement = {
        if (quantity > NUMBER_ZERO) {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updateOrder(
                orderId = orderId,
                objectId = objectId,
                updateObject = UpdateObjectRequestDTO(
                    action = Action.INCREMENT,
                    quantity = quantity
                )
            )
        } else {
            observer = Triple(first = false, second = true, third = NUMBER_EQUALS_ZERO)
        }
    }
    TextField(
        label = QTD,
        value = quantity.toString(),
        onValueChange = {
            quantity = it.toIntOrNull() ?: NUMBER_ZERO
        },
        isError = observer.second,
        message = observer.third,
        modifier = modifier,
        onGo = {
            saveIncrement()
        }
    )
    LoadingButton(
        label = ADD_ITEM,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = INCREMENT_ITEM,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                saveIncrement()
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerIncrementMoreItemsObject(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            onRefresh()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerIncrementMoreItemsObject(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateOrder }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.UPDATE_ORDER)
            onSuccessful()
        }
    )
}

@Composable
private fun DecrementItemsObject(
    modifier: Modifier = Modifier,
    orderId: Long,
    objectId: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val saveDecrement = {
        if (quantity > NUMBER_ZERO) {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updateOrder(
                orderId = orderId,
                objectId = objectId,
                updateObject = UpdateObjectRequestDTO(
                    action = Action.DECREMENT,
                    quantity = quantity
                )
            )
        } else {
            observer = Triple(first = false, second = true, third = NUMBER_EQUALS_ZERO)
        }
    }
    TextField(
        label = QTD,
        value = quantity.toString(),
        onValueChange = {
            quantity = it.toIntOrNull() ?: NUMBER_ZERO
        },
        isError = observer.second,
        message = observer.third,
        modifier = modifier,
        onGo = {
            saveDecrement()
        }
    )
    LoadingButton(
        label = REMOVER_ITEM,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = DECREMENT_ITEM,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                saveDecrement()
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerDecrementItemsObject(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            onRefresh()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerDecrementItemsObject(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateOrder }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetOrder(reset = ResetOrder.UPDATE_ORDER)
            onSuccessful()
        }
    )
}
