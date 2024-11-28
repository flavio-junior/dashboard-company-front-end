package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.common.item.vo.ItemsResponseVO
import br.com.digital.store.components.ui.PageIndicator
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun PageIndicatorItems(
    modifier: Modifier = Modifier,
    content: ItemsResponseVO
) {
    val viewModel: ItemViewModel = getKoin().get()
    Row(
        modifier = modifier
    ) {
        PageIndicator(
            modifier = Modifier.weight(weight = WEIGHT_SIZE_4),
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
