package br.com.digital.store.ui.view.pdv

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.digital.store.components.ui.Description
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.pdv.domain.ItemsPVD
import br.com.digital.store.navigation.RouteApp
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import kotlinx.coroutines.launch

@Composable
fun PdvTabs(
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { ItemsPVD.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
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
            ItemsPVD.entries.forEachIndexed { index, currentTab ->
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
                PdvTabsMain(
                    index = it,
                    goToNextTab = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = it)
                        }
                    },
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = {
                        goToNextScreen(RouteApp.Pdv.item)
                        scope.launch {
                            pagerState.animateScrollToPage(page = -it)
                        }
                    }
                )
            }
        }
    }
}
