package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.EmptyList
import br.com.digital.store.components.ui.HeaderSearch
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.features.product.data.vo.ProductsResponseVO
import br.com.digital.store.features.product.ui.viewmodel.ProductViewModel
import br.com.digital.store.features.product.utils.ProductUtils.CREATE_PRODUCT
import br.com.digital.store.features.product.utils.ProductUtils.EMPTY_LIST_PRODUCTS
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ListProductsScreen(
    onItemSelected: (ProductResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
            .fillMaxSize()
    ) {
        val viewModel: ProductViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllProducts()
        }
        HeaderSearch(
            onSearch = { name, size, sort, route ->
                viewModel.findAllProducts(name = name, size = size, sort = sort, route = route)
            },
            onSort = { name, size, sort, route ->
                viewModel.findAllProducts(name = name, size = size, sort = sort, route = route)
            },
            onFilter = { name, size, sort, route ->
                viewModel.findAllProducts(name = name, size = size, sort = sort, route = route)
            },
            onRefresh = { name, size, sort, route ->
                viewModel.findAllProducts(name = name, size = size, sort = sort, route = route)
            }
        )
        ObserveNetworkStateHandlerProducts(
            viewModel = viewModel,
            onItemSelected = onItemSelected,
            onToCreateNewProduct = onToCreateNewProduct,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerProducts(
    viewModel: ProductViewModel,
    onItemSelected: (ProductResponseVO) -> Unit = {},
    onToCreateNewProduct: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<ProductsResponseVO> by remember { viewModel.findAllProducts }
    val showEmptyList: Boolean by remember { viewModel.showEmptyList }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            if (showEmptyList) {
                EmptyList(
                    title = EMPTY_LIST_PRODUCTS,
                    description = "$CREATE_PRODUCT?",
                    onClick = onToCreateNewProduct,
                    refresh = {
                        viewModel.findAllProducts()
                    }
                )
            } else {
                it.result?.let { response ->
                    ProductsResult(productsResponseVO = response, onItemSelected = onItemSelected)
                } ?: viewModel.showEmptyList(show = true)
            }
        }
    )
}

@Composable
private fun ProductsResult(
    productsResponseVO: ProductsResponseVO,
    onItemSelected: (ProductResponseVO) -> Unit = {}
) {
    Column {
        ListProducts(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize12),
            products = productsResponseVO,
            onItemSelected = onItemSelected
        )
        PageIndicatorProducts(content = productsResponseVO)
    }
}
