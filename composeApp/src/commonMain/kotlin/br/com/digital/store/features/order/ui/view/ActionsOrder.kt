package br.com.digital.store.features.order.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_ITEM
import br.com.digital.store.components.strings.StringsUtils.DELIVERED
import br.com.digital.store.components.strings.StringsUtils.PENDING_DELIVERY
import br.com.digital.store.components.strings.StringsUtils.QTD
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.STATUS_ORDER
import br.com.digital.store.components.strings.StringsUtils.UPDATE
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Button
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.delete
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.domain.others.Action
import br.com.digital.store.features.order.domain.status.ObjectStatus
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.DELETE_OBJECT
import br.com.digital.store.features.order.utils.OrderUtils.DELETE_RESUME
import br.com.digital.store.features.order.utils.OrderUtils.INCREMENT_ITEM
import br.com.digital.store.features.order.utils.OrderUtils.UPDATE_RESUME
import br.com.digital.store.features.order.utils.OrderUtils.UPDATE_STATUS_ITEM
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.NUMBER_EQUALS_ZERO
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.store.utils.deliveryStatus
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateStatusObject(
    modifier: Modifier = Modifier,
    orderId: Long,
    objectId: Long,
    status: String,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var itemSelected: String by remember {
        mutableStateOf(value = status)
    }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: OrderViewModel = getKoin().get()
    DropdownMenu(
        selectedValue = itemSelected,
        items = deliveryStatus,
        label = STATUS,
        onValueChangedEvent = {
            itemSelected = it
        }
    )
    LoadingButton(
        label = UPDATE,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = UPDATE_STATUS_ITEM,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.updateOrder(
                    orderId = orderId,
                    objectId = objectId,
                    updateObject = UpdateObjectRequestDTO(
                        action = Action.UPDATE_STATUS_OBJECT,
                        status = when (itemSelected) {
                            DELIVERED -> ObjectStatus.DELIVERED
                            else -> ObjectStatus.PENDING
                        }
                    )
                )
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerUpdateStatusObject(
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
private fun ObserveNetworkStateHandlerUpdateStatusObject(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateStatusObject }
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
            viewModel.resetOrder(reset = ResetOrder.UPDATE_STATUS_OBJECT)
            onSuccessful()
        }
    )
}

@Composable
fun UpdateStatusOverview(
    orderId: Long,
    objectId: Long,
    overviewId: Long,
    modifier: Modifier = Modifier,
    status: String,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var itemSelected: String by remember {
        mutableStateOf(value = status)
    }
    DropdownMenu(
        selectedValue = itemSelected,
        items = deliveryStatus,
        label = STATUS_ORDER,
        onValueChangedEvent = {
            itemSelected = it
        }
    )
    LoadingButton(
        label = UPDATE,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first,
        modifier = modifier
    )

    if (openDialog) {
        Alert(
            label = UPDATE_RESUME,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.updateOrder(
                    orderId = orderId,
                    objectId = objectId,
                    updateObject = UpdateObjectRequestDTO(
                        action = Action.UPDATE_STATUS_OVERVIEW,
                        overview = overviewId,
                        status = when (itemSelected) {
                            PENDING_DELIVERY -> ObjectStatus.PENDING
                            DELIVERED -> ObjectStatus.DELIVERED
                            else -> ObjectStatus.PENDING
                        }
                    )
                )
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerUpdateStatusOverview(
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
    onError(observer)
}

@Composable
private fun ObserveNetworkStateHandlerUpdateStatusOverview(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateStatusOverview}
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
            viewModel.resetOrder(reset = ResetOrder.UPDATE_STATUS_OVERVIEW)
            onSuccessful()
        }
    )
}

@Composable
fun IncrementOverview(
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
    val saveIncrementOverview = {
        if (quantity > NUMBER_ZERO) {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updateOrder(
                orderId = orderId,
                objectId = objectId,
                updateObject = UpdateObjectRequestDTO(
                    action = Action.INCREMENT_OVERVIEW,
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
            saveIncrementOverview()
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
                saveIncrementOverview()
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerIncrementOverview(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            quantity = NUMBER_ZERO
            onRefresh()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerIncrementOverview(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.incrementOverview }
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
            viewModel.resetOrder(reset = ResetOrder.INCREMENT_OVERVIEW)
            onSuccessful()
        }
    )
}

@Composable
fun RemoveOverview(
    orderId: Long,
    objectId: Long,
    overviewId: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Button(
        icon = Res.drawable.delete,
        onClick = {
            openDialog = true
        }
    )
    if (openDialog) {
        Alert(
            label = DELETE_RESUME,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                viewModel.updateOrder(
                    orderId = orderId,
                    objectId = objectId,
                    updateObject = UpdateObjectRequestDTO(
                        action = Action.REMOVE_OVERVIEW,
                        overview = overviewId
                    )
                )
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerRemoveOverview(
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
    onError(observer)
}

@Composable
private fun ObserveNetworkStateHandlerRemoveOverview(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.removeOverview }
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
            viewModel.resetOrder(reset = ResetOrder.REMOVE_OVERVIEW)
            onSuccessful()
        }
    )
}

@Composable
fun RemoveObject(
    orderId: Long,
    objectId: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Button(
        icon = Res.drawable.delete,
        onClick = {
            openDialog = true
        }
    )
    if (openDialog) {
        Alert(
            label = DELETE_OBJECT,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.updateOrder(
                    orderId = orderId,
                    objectId = objectId,
                    updateObject = UpdateObjectRequestDTO(action = Action.REMOVE_OBJECT)
                )
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerRemoveObject(
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
private fun ObserveNetworkStateHandlerRemoveObject(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.removeObject }
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
            viewModel.resetOrder(reset = ResetOrder.REMOVE_OBJECT)
            onSuccessful()
        }
    )
}
