package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.PENDING_OBJECTS
import br.com.digital.store.components.strings.StringsUtils.VALUE_TOTAL
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.utils.OrderUtils.NUMBER_ITEMS
import br.com.digital.store.features.order.utils.OrderUtils.NUMBER_RESERVATIONS
import br.com.digital.store.features.order.utils.countPendingObjects
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.formatterMaskToMoney
import br.com.digital.store.utils.onBorder

@Composable
fun CardReservation(
    orderResponseVO: OrderResponseVO,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize4),
        modifier = Modifier
            .onBorder(
                onClick = { onItemSelected(Pair(first = orderResponseVO, second = NUMBER_ONE)) },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize12)
    ) {
        CreateAtOrder(createAt = orderResponseVO.createdAt)
        ItemReservation(
            label = NUMBER_RESERVATIONS,
            body = {
                SimpleText(text = orderResponseVO.reservations?.size.toString())
            }
        )
        ItemReservation(
            label = NUMBER_ITEMS,
            body = {
                SimpleText(text = orderResponseVO.quantity.toString())
            }
        )
        ItemReservation(
            label = PENDING_OBJECTS,
            body = {
                SimpleText(text = countPendingObjects(order = orderResponseVO))
            }
        )
        ItemReservation(
            label = VALUE_TOTAL,
            body = {
                SimpleText(text = formatterMaskToMoney(price = orderResponseVO.price))
            }
        )
    }
}

@Composable
private fun ItemReservation(
    label: String,
    body: @Composable () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize4)
    ) {
        SimpleText(text = label)
        body()
    }
}
