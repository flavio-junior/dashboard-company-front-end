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
    onToCreateNewProduct: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> ListProductsScreen(
            onItemSelected = onItemSelected,
            onToCreateNewProduct = onToCreateNewProduct,
            goToAlternativeRoutes = goToAlternativeRoutes
        )

        NumbersUtils.NUMBER_ONE -> DetailsProductScreen(
            product = productsResponseVO,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = { onRefresh(index) }
        )

        NumbersUtils.NUMBER_TWO -> CreateNewProductScreen()
    }
}
