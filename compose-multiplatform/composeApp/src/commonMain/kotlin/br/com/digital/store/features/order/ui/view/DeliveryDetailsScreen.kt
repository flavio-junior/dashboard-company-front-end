package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADDRESS
import br.com.digital.store.components.strings.StringsUtils.COMPLEMENT
import br.com.digital.store.components.strings.StringsUtils.DISTRICT
import br.com.digital.store.components.strings.StringsUtils.NUMBER
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.STREET
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.AddressResponseVO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.factory.addressFactory
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2

@Composable
fun DeliveryDetailsScreen(
    orderResponseVO: OrderResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        HeaderDetailsOrder(orderResponseVO = orderResponseVO)
        AddressOrder(addressResponseVO = orderResponseVO.address)
        orderResponseVO.objects?.let {
            Object(
                orderResponseVO = orderResponseVO,
                objects = it,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )
        }
    }
}

@Composable
private fun AddressOrder(
    addressResponseVO: AddressResponseVO? = null
) {
    Description(description = ADDRESS)
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        TextField(
            enabled = false,
            label = STATUS,
            value = addressFactory(status = addressResponseVO?.status),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = STREET,
            value = addressResponseVO?.street ?: EMPTY_TEXT,
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = NUMBER,
            value = addressResponseVO?.number.toString(),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = DISTRICT,
            value = addressResponseVO?.district ?: EMPTY_TEXT,
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = COMPLEMENT,
            value = addressResponseVO?.complement ?: EMPTY_TEXT,
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
        )
    }
}
