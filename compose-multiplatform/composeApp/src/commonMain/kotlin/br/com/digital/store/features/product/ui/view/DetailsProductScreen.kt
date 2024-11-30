package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CATEGORIES
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.formatterMaskToMoney

@Composable
fun DetailsProductScreen(
    product: ProductResponseVO
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        ) {
            TextField(
                enabled = false,
                label = NAME,
                value = product.name,
                onValueChange = {},
                modifier = Modifier.weight(weight = CommonUtils.WEIGHT_SIZE_2)
            )
            TextField(
                enabled = false,
                label = PRICE,
                value = formatterMaskToMoney(price = product.price),
                onValueChange = {},
                modifier = Modifier.weight(weight = CommonUtils.WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = QUANTITY,
                value = product.quantity.toString(),
                onValueChange = {},
                modifier = Modifier.weight(weight = CommonUtils.WEIGHT_SIZE)
            )
        }
        Description(description = CATEGORIES)
        product.categories?.forEach {
            Tag(text = it.name, value = it, enabled = false)
        }
    }
}
