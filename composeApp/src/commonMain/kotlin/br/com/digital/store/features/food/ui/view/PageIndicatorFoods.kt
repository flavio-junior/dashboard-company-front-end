package br.com.digital.store.features.food.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.PageIndicator
import br.com.digital.store.features.food.data.vo.FoodsResponseVO
import br.com.digital.store.features.food.ui.viewmodel.FoodViewModel
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun PageIndicatorFoods(
    modifier: Modifier = Modifier,
    content: FoodsResponseVO
) {
    val viewModel: FoodViewModel = getKoin().get()
    Row(
        modifier = modifier
    ) {
        PageIndicator(
            modifier = modifier.fillMaxWidth(),
            currentPage = content.pageable.pageNumber,
            totalPages = content.totalPages,
            loadNextPage = {
                viewModel.loadNextPage()
            },
            reloadPreviousPage = {
                viewModel.reloadPreviousPage()
            }
        )
        Spacer(modifier = Modifier.weight(weight = WEIGHT_SIZE_2))
    }
}
