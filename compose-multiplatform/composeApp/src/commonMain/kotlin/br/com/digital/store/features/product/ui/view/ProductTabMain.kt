package br.com.digital.store.features.product.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.utils.NumbersUtils

@Composable
fun ProductTabMain(
    index: Int,
    productsResponseVO: ProductResponseVO = ProductResponseVO(),
    onItemSelected: (ProductResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> ListProductsScreen(
            onItemSelected = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes
        )

        NumbersUtils.NUMBER_ONE -> DetailsProductScreen(
            product = productsResponseVO,
            goToAlternativeRoutes
        )

        NumbersUtils.NUMBER_TWO -> UpdateProductScreen()
    }
}
