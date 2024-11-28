package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.features.item.data.vo.ItemResponseVO
import br.com.digital.store.features.item.data.vo.ItemsResponseVO
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.HeaderSearch
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CardItems(
    modifier: Modifier = Modifier,
    onItemSelected: (ItemResponseVO) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
    ) {
        val viewModel: ItemViewModel = getKoin().get()
        HeaderSearch(
            onSearch = { name, size, sort, route ->
                viewModel.findAllItems(name = name, size = size, sort = sort, route = route)
            },
            onSort = { name, size, sort, route ->
                viewModel.findAllItems(name = name, size = size, sort = sort, route = route)
            },
            onFilter = { name, size, sort, route ->
                viewModel.findAllItems(name = name, size = size, sort = sort, route = route)
            },
            onRefresh = { name, size, sort, route ->
                viewModel.findAllItems(name = name, size = size, sort = sort, route = route)
            }
        )
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllItems()
        }
        ObserveNetworkStateHandlerItems(viewModel = viewModel, onItemSelected = onItemSelected)
    }
}

@Composable
private fun ObserveNetworkStateHandlerItems(
    viewModel: ItemViewModel,
    onItemSelected: (ItemResponseVO) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<ItemsResponseVO> by remember { viewModel.findAllItems }
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
                ItemsResult(content = response, onItemSelected)
            }
        }
    )
}

@Composable
private fun ItemsResult(
    content: ItemsResponseVO,
    onItemSelected: (ItemResponseVO) -> Unit = {}
) {
    Column {
        ListItems(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize8),
            content = content,
            onItemSelected = onItemSelected
        )
        PageIndicatorItems(content = content)
        SaveItem()
    }
}
