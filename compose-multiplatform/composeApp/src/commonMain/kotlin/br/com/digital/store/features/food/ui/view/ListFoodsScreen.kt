package br.com.digital.store.features.food.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.EmptyList
import br.com.digital.store.components.ui.HeaderSearch
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.food.data.vo.FoodResponseVO
import br.com.digital.store.features.food.data.vo.FoodsResponseVO
import br.com.digital.store.features.food.ui.viewmodel.FoodViewModel
import br.com.digital.store.features.food.utils.FoodUtils.CREATE_FOOD
import br.com.digital.store.features.food.utils.FoodUtils.EMPTY_LIST_FOODS
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ListFoodsScreen(
    onItemSelected: (FoodResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onToCreateNewFood: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
            .fillMaxSize()
    ) {
        val viewModel: FoodViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllFoods()
        }
        HeaderSearch(
            onSearch = { name, size, sort, route ->
                viewModel.findAllFoods(name = name, size = size, sort = sort, route = route)
            },
            onSort = { name, size, sort, route ->
                viewModel.findAllFoods(name = name, size = size, sort = sort, route = route)
            },
            onFilter = { name, size, sort, route ->
                viewModel.findAllFoods(name = name, size = size, sort = sort, route = route)
            },
            onRefresh = { name, size, sort, route ->
                viewModel.findAllFoods(name = name, size = size, sort = sort, route = route)
            }
        )
        ObserveNetworkStateHandlerFood(
            viewModel = viewModel,
            onItemSelected = onItemSelected,
            onToCreateNewFood = onToCreateNewFood,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerFood(
    viewModel: FoodViewModel,
    onItemSelected: (FoodResponseVO) -> Unit = {},
    onToCreateNewFood: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<FoodsResponseVO> by remember { viewModel.findAllFoods }
    val showEmptyList: Boolean by remember { viewModel.showEmptyList }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            if (showEmptyList) {
                EmptyList(
                    title = EMPTY_LIST_FOODS,
                    description = "$CREATE_FOOD?",
                    onClick = onToCreateNewFood,
                    refresh = {
                        viewModel.findAllFoods()
                    }
                )
            } else {
                it.result?.let { response ->
                    FoodResult(foodResponseVO = response, onItemSelected = onItemSelected)
                } ?: viewModel.showEmptyList(show = true)
            }
        }
    )
}

@Composable
private fun FoodResult(
    foodResponseVO: FoodsResponseVO,
    onItemSelected: (FoodResponseVO) -> Unit = {}
) {
    Column {
        ListFood(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize12),
            foods = foodResponseVO,
            onItemSelected = onItemSelected
        )
        PageIndicatorFoods(content = foodResponseVO)
    }
}
