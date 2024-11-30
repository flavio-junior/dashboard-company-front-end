package br.com.digital.store.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.utils.NumbersUtils

@Composable
fun ProductTabMain(
    index: Int,
    productsResponseVO: ProductResponseVO = ProductResponseVO(),
    onItemSelected: (ProductResponseVO) -> Unit = {}
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> ListProductsScreen(onItemSelected = onItemSelected)
        NumbersUtils.NUMBER_ONE -> DetailsProductScreen(product = productsResponseVO)
        NumbersUtils.NUMBER_TWO -> UpdateProductScreen()
    }
}
