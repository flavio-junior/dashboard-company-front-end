package br.com.digital.store.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.utils.NumbersUtils

@Composable
fun ProductTabMain(
    index: Int
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> ListProductsScreen()
        NumbersUtils.NUMBER_ONE -> DetailsProductScreen()
        NumbersUtils.NUMBER_TWO -> UpdateProductScreen()
    }
}
