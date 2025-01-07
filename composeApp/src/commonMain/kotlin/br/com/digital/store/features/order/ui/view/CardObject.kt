package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import br.com.digital.store.components.strings.StringsUtils.ITEMS
import br.com.digital.store.components.ui.ButtonCreate
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.factory.objectFactory
import br.com.digital.store.features.order.domain.factory.statusDeliveryStatus
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.utils.OrderUtils.ADD_MORE_ITEMS_ORDER
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_FOUR
import br.com.digital.store.utils.formatterMaskToMoney
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch

@Composable
fun Object(
    orderResponseVO: OrderResponseVO,
    objects: List<ObjectResponseVO>,
    type: TypeOrder? = null,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {},
    objectSelected: (Pair<Long, ObjectResponseVO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column {
        ListObject(
            orderResponseVO = orderResponseVO,
            objects = objects,
            type = type,
            objectSelected = objectSelected,
            addMoreItems = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
    }
}

@Composable
private fun ListObject(
    orderResponseVO: OrderResponseVO,
    modifier: Modifier = Modifier,
    objects: List<ObjectResponseVO>,
    type: TypeOrder? = null,
    objectSelected: (Pair<Long, ObjectResponseVO>) -> Unit = {},
    addMoreItems: (Pair<OrderResponseVO, Int>) -> Unit = {},
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
        ItemObject(
            spaceBy = Themes.size.spaceSize0,
            body = {
                ButtonCreate(
                    label = ADD_MORE_ITEMS_ORDER,
                    onItemSelected = {
                        addMoreItems(Pair(first = orderResponseVO, second = NUMBER_FOUR))
                    }
                )
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
                            orderId = orderResponseVO.id,
                            objectResponseVO = objectResult,
                            selected = selectedIndex == index,
                            objectSelected = objectSelected,
                            onDisableItem = {
                                selectedIndex = index
                            }
                        )
                    }
                }
            }
        )
        FooterOrder(
            orderId = orderResponseVO.id,
            status = statusDeliveryStatus(status = orderResponseVO.address?.status),
            type = type,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
private fun CardObject(
    orderId: Long,
    objectResponseVO: ObjectResponseVO,
    selected: Boolean = false,
    objectSelected: (Pair<Long, ObjectResponseVO>) -> Unit = {},
    onDisableItem: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    objectSelected(Pair(first = orderId, second = objectResponseVO))
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
