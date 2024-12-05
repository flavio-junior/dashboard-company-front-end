package br.com.digital.store.features.food.ui.view

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
import br.com.digital.store.features.food.data.vo.FoodResponseVO
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils
import kotlinx.coroutines.launch

@Composable
fun FoodTabs(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { ItemsFood.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    var foodResponseVO: FoodResponseVO by remember { mutableStateOf(value = FoodResponseVO()) }
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
            ItemsFood.entries.forEachIndexed { index, currentTab ->
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
                FoodTabMain(
                    index = it,
                    foodResponseVO = foodResponseVO,
                    onItemSelected = {
                        foodResponseVO = it
                        scope.launch {
                            pagerState.animateScrollToPage(page = NumbersUtils.NUMBER_ONE)
                        }
                    },
                    onToCreateNewFood = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = + NumbersUtils.NUMBER_TWO)
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
