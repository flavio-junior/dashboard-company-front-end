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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import br.com.digital.store.components.strings.StringsUtils.ADD_ITEM
import br.com.digital.store.components.strings.StringsUtils.DETAILS
import br.com.digital.store.components.strings.StringsUtils.ITEMS
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QTD
import br.com.digital.store.components.strings.StringsUtils.REMOVER_ITEM
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.UPDATE
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.domain.factory.objectFactory
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.SpaceSize.spaceSize4
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_3
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.store.utils.deliveryStatus
import br.com.digital.store.utils.formatterMaskToMoney
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch

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
            modifier = Modifier.weight(weight = WEIGHT_SIZE_2),
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
    }
}

@Composable
private fun DetailsObject(
    modifier: Modifier = Modifier,
    objectResponseVO: ObjectResponseVO
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
        ItemObject(
            body = {
                var itemSelected: String by remember {
                    mutableStateOf(value = status)
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
                    onClick = {},
                    modifier = Modifier.weight(weight = 1.2f)
                )
            }
        )
        ItemObject(
            body = {
                var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
                TextField(
                    label = QTD,
                    value = quantity.toString(),
                    onValueChange = {
                        quantity = it.toIntOrNull() ?: NUMBER_ZERO
                    },
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
                LoadingButton(
                    label = ADD_ITEM,
                    onClick = {},
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
            }
        )
        ItemObject(
            body = {
                var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
                TextField(
                    label = QTD,
                    value = quantity.toString(),
                    onValueChange = {
                        quantity = it.toIntOrNull() ?: NUMBER_ZERO
                    },
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
                LoadingButton(
                    label = REMOVER_ITEM,
                    onClick = {},
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
            }
        )
    }
}
