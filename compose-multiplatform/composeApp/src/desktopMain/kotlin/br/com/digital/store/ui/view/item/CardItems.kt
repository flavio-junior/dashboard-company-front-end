package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_5
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CardItems() {
    Column(
        modifier = Modifier
            .padding(all = Themes.size.spaceSize16)
    ) {
        HeaderSearchItems()
        val viewModel: ItemViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllItems()
        }
        ObserveNetworkStateHandlerItems(viewModel = viewModel)
    }
}

@Composable
private fun ObserveNetworkStateHandlerItems(
    viewModel: ItemViewModel
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
                AllServicesItems(content = response)
            }
        }
    )
}

@Composable
private fun AllServicesItems(
    content: ItemsResponseVO
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize().weight(weight = WEIGHT_SIZE_5)
                .weight(weight = WEIGHT_SIZE_4)
        ) {
            var item: ItemResponseVO by remember { mutableStateOf(value = ItemResponseVO()) }
            ListItems(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = WEIGHT_SIZE_4)
                    .padding(top = Themes.size.spaceSize8),
                content = content,
                onItemSelected = { item = it }
            )
            EditItem(
                itemVO = item,
                modifier = Modifier
                    .padding(start = Themes.size.spaceSize16, top = Themes.size.spaceSize16)
                    .weight(weight = WEIGHT_SIZE_2),
                onCleanItem = {
                    item = ItemResponseVO()
                },
                onSuccessful = {
                    item = it
                }
            )
        }
        PageIndicatorItems(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            content = content
        )
        Row(
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        ) {
            SaveItem(modifier = Modifier.weight(weight = WEIGHT_SIZE_4))
            Spacer(modifier = Modifier.weight(weight = WEIGHT_SIZE_2))
        }
    }
}
