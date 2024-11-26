package br.com.digital.store.ui.view.category

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
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_5
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CardCategories(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val viewModel: CategoryViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllCategories()
        }
        ObserveNetworkStateHandlerCategories(viewModel = viewModel)
    }
}

@Composable
private fun ObserveNetworkStateHandlerCategories(
    viewModel: CategoryViewModel
) {
    val state: ObserveNetworkStateHandler<List<CategoryResponseVO>> by remember { viewModel.findAllCategories }
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
                AllServicesCategories(categories = response)
            }
        }
    )
}

@Composable
private fun AllServicesCategories(
    categories: List<CategoryResponseVO>
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize().weight(weight = WEIGHT_SIZE_5)
                .weight(weight = WEIGHT_SIZE_4)
        ) {
            var category: CategoryResponseVO by remember { mutableStateOf(value = CategoryResponseVO()) }
            ListCategories(
                categories = categories,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = WEIGHT_SIZE_4),
                onItemSelected = { category = it }
            )
            EditCategory(
                categoryVO = category,
                modifier = Modifier
                    .padding(start = Themes.size.spaceSize16)
                    .weight(weight = WEIGHT_SIZE_2),
                onSuccessful = {
                    category = it
                }
            )
        }
        Row(
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        ) {
            SaveCategory(modifier = Modifier.weight(weight = WEIGHT_SIZE_4))
            Spacer(modifier = Modifier.weight(weight = WEIGHT_SIZE_2))
        }
    }
}
