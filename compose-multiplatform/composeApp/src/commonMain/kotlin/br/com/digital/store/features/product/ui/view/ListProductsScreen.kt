package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.HeaderSearch
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.features.product.data.vo.ProductsResponseVO
import br.com.digital.store.features.product.ui.viewmodel.ProductViewModel
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ListProductsScreen(
    onItemSelected: (ProductResponseVO) -> Unit = {}
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
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllProducts()
        }
        ObserveNetworkStateHandlerCategories(viewModel = viewModel, onItemSelected = onItemSelected)
    }
}

@Composable
private fun ObserveNetworkStateHandlerCategories(
    viewModel: ProductViewModel,
    onItemSelected: (ProductResponseVO) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<ProductsResponseVO> by remember { viewModel.findAllProducts }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Description(description = it)
        },
        onSuccess = {
            it.result?.let { response ->
                ProductsResult(content = response, onItemSelected = onItemSelected)
            }
        }
    )
}

@Composable
private fun ProductsResult(
    content: ProductsResponseVO,
    onItemSelected: (ProductResponseVO) -> Unit = {}
) {
    Column {
        ListProducts(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize12),
            content = content,
            onItemSelected = onItemSelected
        )
        PageIndicatorProducts(content = content)
    }
}
