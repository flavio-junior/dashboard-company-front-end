package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.digital.store.components.ui.Description
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import kotlinx.coroutines.launch

@Composable
fun OrdersTabs(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { ItemsOrder.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    var ordersResponseVO: OrderResponseVO by remember { mutableStateOf(value = OrderResponseVO()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Themes.size.spaceSize36)
    ) {
        ScrollableTabRow(
            backgroundColor = Color.Transparent,
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxWidth()
        ) {
            ItemsOrder.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Description(description = currentTab.text)
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = WEIGHT_SIZE)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                OrderTabMain(
                    index = it,
                    orderResponseVO = ordersResponseVO,
                    onItemSelected = {
                        ordersResponseVO = it.first
                        scope.launch {
                            pagerState.animateScrollToPage(page = it.second)
                        }
                    },
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = - it)
                        }
                    }
                )
            }
        }
    }
}
