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
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ResourceUnavailable
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.features.product.utils.ProductUtils.DETAILS_PRODUCT
import br.com.digital.store.features.product.utils.ProductUtils.UPDATE_PRODUCT
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.store.utils.formatterMaskToMoney

@Composable
fun DetailsProductScreen(
    product: ProductResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    if (product.id > NUMBER_ZERO) {
        DetailsProductBody(product = product, goToAlternativeRoutes = goToAlternativeRoutes)
    } else {
        ResourceUnavailable()
    }
}

@Composable
fun DetailsProductBody(
    product: ProductResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = "$DETAILS_PRODUCT:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = product.id.toString(),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = NAME,
                value = product.name,
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
            TextField(
                enabled = false,
                label = PRICE,
                value = formatterMaskToMoney(price = product.price),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = QUANTITY,
                value = product.quantity.toString(),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
        }
        Description(description = "$CATEGORIES:")
        product.categories?.forEach {
            Tag(text = it.name, value = it, enabled = false)
        }
        Description(description = UPDATE_PRODUCT)
        UpdateProduct(id = product.id, goToAlternativeRoutes = goToAlternativeRoutes)
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        ) {
            UpdatePriceProduct(
                id = product.id,
                goToAlternativeRoutes = goToAlternativeRoutes,
                modifier = Modifier
                    .weight(weight = WEIGHT_SIZE_2)
            )
            RestockProduct(
                id = product.id,
                goToAlternativeRoutes = goToAlternativeRoutes,
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
        }
    }
}
