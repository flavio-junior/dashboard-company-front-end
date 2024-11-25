package br.com.digital.store.ui.view.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CardCategories() {
    val viewModel: CategoryViewModel = getKoin().get()
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllCategories()
    }
    ObserveNetworkStateHandlerCategories(viewModel = viewModel)
}

@Composable
private fun ObserveNetworkStateHandlerCategories(
    viewModel: CategoryViewModel
) {
    val state: ObserveNetworkStateHandler<List<CategoryResponseVO>> by remember { viewModel.findAllCategories }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {
            Description(description = "Loading")
        },
        onError = {
            Description(description = it)
        },
        onSuccess = {
            it.result?.let { response ->
                response.forEach {
                    Description(description = it.name)
                }
            }
        }
    )
}
