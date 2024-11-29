package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.product.utils.ProductUtils.PRODUCTS
import br.com.digital.store.theme.Theme
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import kotlinx.coroutines.launch

enum class ProductTabs(val text: String) {
    LIST(text = "Lista de Produtos"),
    DETAIL(text = "Detalhes do Produto"),
    CREATE(text = "Criar Produto")
}

enum class Tabs(val text: String) {
    ListProducts(text = "Lista de Produtos"),
    DetailsProduct(text = "Detalhes do Produto"),
    UpdateProduct(text = "Update do Produto")
}

@Composable
fun Tabs() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { Tabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Title(title = PRODUCTS)
                    },
                    backgroundColor = Themes.colors.background,
                    elevation = Themes.size.spaceSize0
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
            ) {
                ScrollableTabRow(
                    backgroundColor = Color.Transparent,
                    selectedTabIndex = selectedTabIndex.value,
                    modifier = Modifier
                        .background(color = Themes.colors.background)
                        .fillMaxWidth()
                ) {
                    Tabs.entries.forEachIndexed { index, currentTab ->
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
                        ProductTabMain(index = it)
                        //Text(text = Tabs.entries[selectedTabIndex.value].text)
                    }
                }
            }
        }
    }
}