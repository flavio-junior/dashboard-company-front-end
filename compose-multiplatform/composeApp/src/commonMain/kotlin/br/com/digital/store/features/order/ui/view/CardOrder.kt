package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.VALUE_TOTAL
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.utils.OrderUtils.QUANTITY_ITEMS
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.formatterMaskToMoney
import br.com.digital.store.utils.onBorder

@Composable
fun CardOrder(
    orderResponseVO: OrderResponseVO,
    onItemSelected: (OrderResponseVO) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize4),
        modifier = Modifier
            .onBorder(
                onClick = { onItemSelected(orderResponseVO) },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize12)
    ) {
        CreateAtOrder(createAt = orderResponseVO.createdAt)
        ItemOrder(
            label = QUANTITY_ITEMS,
            body = {
                SimpleText(text = orderResponseVO.quantity.toString())
            }
        )
        ItemOrder(
            label = VALUE_TOTAL,
            body = {
                SimpleText(text = formatterMaskToMoney(price = orderResponseVO.price))
            }
        )
    }
}

@Composable
private fun ItemOrder(
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
