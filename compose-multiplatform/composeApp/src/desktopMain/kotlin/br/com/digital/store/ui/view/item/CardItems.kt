package br.com.digital.store.ui.view.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.common.item.vo.ItemResponseVO
import br.com.digital.store.common.item.vo.ItemsResponseVO
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
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
        HeaderSearchItems()
        val viewModel: ItemViewModel = getKoin().get()
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
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(color = Themes.colors.success)
                .weight(weight = WEIGHT_SIZE)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SaveItem()
        }
    }
}
