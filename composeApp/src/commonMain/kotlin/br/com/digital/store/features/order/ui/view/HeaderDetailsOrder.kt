package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.mask.formatDate
import br.com.digital.store.components.strings.StringsUtils.CREATE_AT
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.strings.StringsUtils.TYPE_ORDER
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.factory.detailsOrderFactory
import br.com.digital.store.features.order.utils.OrderUtils.DETAILS_ORDER
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.formatterMaskToMoney

@Composable
fun HeaderDetailsOrder(
    orderResponseVO: OrderResponseVO
) {
    Description(description = "$DETAILS_ORDER:")
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        TextField(
            enabled = false,
            label = ID,
            value = orderResponseVO.id.toString(),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = TYPE_ORDER,
            value = detailsOrderFactory(type = orderResponseVO.type),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = CREATE_AT,
            value = formatDate(originalDate = orderResponseVO.createdAt),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = QUANTITY,
            value = orderResponseVO.quantity.toString(),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = PRICE,
            value = formatterMaskToMoney(price = orderResponseVO.total),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}
