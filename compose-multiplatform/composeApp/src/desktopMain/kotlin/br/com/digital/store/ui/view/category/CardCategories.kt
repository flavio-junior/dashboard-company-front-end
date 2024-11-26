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
import br.com.digital.store.common.category.vo.CategoriesResponseVO
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
    val state: ObserveNetworkStateHandler<CategoriesResponseVO> by remember { viewModel.findAllCategories }
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
                AllServicesCategories(
                    content = response,
                    findAllCategories = { parameters ->
                        viewModel.stateSearch(sort = parameters.second)
                        viewModel.findAllCategories(
                            name = parameters.first,
                            sort = parameters.second
                        )
                    }
                )
            }
        }
    )
}

@Composable
private fun AllServicesCategories(
    content: CategoriesResponseVO,
    findAllCategories: (Pair<String, String>) -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize().weight(weight = WEIGHT_SIZE_5)
                .weight(weight = WEIGHT_SIZE_4)
        ) {
            var category: CategoryResponseVO by remember { mutableStateOf(value = CategoryResponseVO()) }
            ListCategories(
                categories = content.content,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = WEIGHT_SIZE_4),
                onItemSelected = { category = it },
                findAllCategories = findAllCategories
            )
            EditCategory(
                categoryVO = category,
                modifier = Modifier
                    .padding(start = Themes.size.spaceSize16)
                    .weight(weight = WEIGHT_SIZE_2),
                onCleanCategory = {
                    category = CategoryResponseVO()
                },
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
