package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import br.com.digital.store.components.strings.StringsUtils.DETAILS
import br.com.digital.store.components.strings.StringsUtils.ITEMS
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QTD
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.UPDATE
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.features.order.data.dto.UpdateObjectRequestDTO
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.domain.factory.objectFactory
import br.com.digital.store.features.order.domain.others.Action
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.DELETE_ITEM
import br.com.digital.store.features.order.utils.OrderUtils.DELETE_OBJECT
import br.com.digital.store.features.order.utils.OrderUtils.UPDATE_STATUS
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.SpaceSize.spaceSize4
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_3
import br.com.digital.store.utils.deliveryStatus
import br.com.digital.store.utils.formatterMaskToMoney
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun Object(
    orderId: Long,
    objects: List<ObjectResponseVO>,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var itemSelected: ObjectResponseVO by remember { mutableStateOf(value = ObjectResponseVO()) }
    Row {
        ListObject(
            orderId = orderId,
            modifier = Modifier
                .weight(weight = WEIGHT_SIZE_3),
            objects = objects,
            onItemSelected = {
                itemSelected = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
        DetailsObject(
            objectResponseVO = itemSelected,
            orderId = orderId,
            modifier = Modifier.weight(weight = WEIGHT_SIZE_2),
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
    }
}

@Composable
private fun ListObject(
    orderId: Long,
    modifier: Modifier = Modifier,
    objects: List<ObjectResponseVO>,
    onItemSelected: (ObjectResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        Description(description = "$ITEMS:")
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var selectedIndex by remember { mutableStateOf(value = -1) }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            state = scrollState,
            modifier = Modifier
                .background(color = Themes.colors.background)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    },
                )
                .padding(horizontal = Themes.size.spaceSize16)
        ) {
            itemsIndexed(items = objects) { index, objectResult ->
                CardObject(
                    orderId = orderId,
                    objectResponseVO = objectResult,
                    selected = selectedIndex == index,
                    onItemSelected = onItemSelected,
                    onDisableItem = {
                        selectedIndex = index
                    }
                )
            }
        }
        ItemObject(
            modifier = Modifier.padding(
                top = Themes.size.spaceSize8,
                end = Themes.size.spaceSize16
            ),
            body = {
                CloseOrder(
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                )
                DeleteOrder(
                    orderId = orderId,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = onRefresh
                )
            }
        )
    }
}

@Composable
private fun CardObject(
    orderId: Long,
    objectResponseVO: ObjectResponseVO,
    selected: Boolean = false,
    onItemSelected: (ObjectResponseVO) -> Unit = {},
    onDisableItem: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    onItemSelected(objectResponseVO)
                    onDisableItem()
                },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = if (selected) ITEM_SELECTED else Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .height(height = Themes.size.spaceSize200)
            .width(width = Themes.size.spaceSize200)
    ) {
        SimpleText(
            text = objectResponseVO.name,
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        SimpleText(
            text = objectResponseVO.quantity.toString(),
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        SimpleText(
            text = objectFactory(status = objectResponseVO.status),
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        SimpleText(
            text = formatterMaskToMoney(price = objectResponseVO.total),
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        DeleteObject(
            orderId = orderId,
            objectId = objectResponseVO.id,
        )
    }
}

@Composable
private fun DeleteObject(
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
    LoadingButton(
        label = DELETE_ITEM,
        onClick = {
            openDialog = true
        },
        isEnabled = observer.first
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
    ObserveNetworkStateHandlerDeleteObject(
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
private fun ObserveNetworkStateHandlerDeleteObject(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteObject }
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
            viewModel.resetOrder(reset = ResetOrder.DELETE_ORDER)
            onSuccessful()
        }
    )
}

@Composable
private fun DetailsObject(
    modifier: Modifier = Modifier,
    orderId: Long,
    objectResponseVO: ObjectResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .drawBehind {
                val strokeWidth = spaceSize4.toPx()
                drawLine(
                    color = Color.Black,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = size.height),
                    strokeWidth = strokeWidth
                )
            }
            .fillMaxHeight()
            .background(color = Themes.colors.background)
            .padding(start = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = DETAILS)
        ItemObject(
            body = {
                TextField(
                    enabled = false,
                    label = NAME,
                    value = objectResponseVO.name,
                    onValueChange = {},
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
                TextField(
                    enabled = false,
                    label = PRICE,
                    value = formatterMaskToMoney(price = objectResponseVO.price),
                    onValueChange = {},
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
            }
        )
        val status = objectFactory(status = objectResponseVO.status)
        ItemObject(
            body = {
                TextField(
                    enabled = false,
                    label = QTD,
                    value = objectResponseVO.quantity.toString(),
                    onValueChange = {},
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
                TextField(
                    enabled = false,
                    label = PRICE,
                    value = formatterMaskToMoney(price = objectResponseVO.total),
                    onValueChange = {},
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
                TextField(
                    enabled = false,
                    label = STATUS,
                    value = status,
                    onValueChange = {},
                    modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
                )
            }
        )
        UpdateStatusOrder(status = status)
        UpdateObject(
            orderId = orderId, objectId = objectResponseVO.id,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
    }
}

@Composable
private fun UpdateStatusOrder(
    status: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var openDialog: Boolean by remember { mutableStateOf(value = false) }
        var itemSelected: String by remember {
            mutableStateOf(value = status)
        }
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        DropdownMenu(
            selectedValue = itemSelected,
            items = deliveryStatus,
            label = STATUS,
            onValueChangedEvent = {
                itemSelected = it
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
        )
        LoadingButton(
            label = UPDATE,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first,
            modifier = Modifier.weight(weight = 1.2f)
        )
        if (openDialog) {
            Alert(
                label = UPDATE_STATUS,
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    openDialog = false
                }
            )
        }
    }
}
