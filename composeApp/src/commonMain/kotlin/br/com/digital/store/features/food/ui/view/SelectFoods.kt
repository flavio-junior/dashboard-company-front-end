package br.com.digital.store.features.food.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import br.com.digital.store.components.strings.StringsUtils.ADD_FOODS
import br.com.digital.store.components.strings.StringsUtils.CANCEL
import br.com.digital.store.components.strings.StringsUtils.CONFIRM
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Search
import br.com.digital.store.components.ui.SimpleButton
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.food.data.dto.FoodResponseDTO
import br.com.digital.store.features.food.ui.viewmodel.FoodViewModel
import br.com.digital.store.features.food.ui.viewmodel.ResetFood
import br.com.digital.store.features.food.utils.FoodUtils.NO_FOOD_SELECTED
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SelectFoods(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (List<FoodResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: FoodViewModel = getKoin().get()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .size(size = Themes.size.spaceSize500)
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            val foods = remember { mutableStateListOf<FoodResponseDTO>() }
            var foodsSelected = remember { mutableStateListOf<FoodResponseDTO>() }
            Title(title = ADD_FOODS)
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findFoodByName(name = name)
                }
            )
            SelectFoods(
                Foods = foods,
                FoodsSelected = foodsSelected,
                onResult = {
                    foodsSelected = it.toMutableStateList()
                }
            )
            ListFoodsAvailable(Foods = foodsSelected)
            FooterSelectFoods(
                onDismissRequest = {
                    viewModel.resetFood(reset = ResetFood.FIND_FOOD_BY_NAME)
                    onDismissRequest()
                },
                onConfirmation = {
                    if (foodsSelected.isNotEmpty()) {
                        viewModel.resetFood(reset = ResetFood.FIND_FOOD_BY_NAME)
                        onConfirmation(foodsSelected)
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_FOOD_SELECTED)
                    }
                }
            )
            ObserveNetworkStateHandlerFindFoodByName(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    foods.clear()
                    foods.addAll(it)
                }
            )
        }
    }
}

@Composable
private fun SelectFoods(
    Foods: List<FoodResponseDTO>,
    FoodsSelected: MutableList<FoodResponseDTO>,
    onResult: (List<FoodResponseDTO>) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(Foods) { Foods ->
            Tag(
                text = Foods.name,
                value = Foods,
                enabled = FoodsSelected.contains(Foods),
                onCheck = { isChecked ->
                    if (isChecked) {
                        FoodsSelected.add(Foods)
                    } else {
                        FoodsSelected.remove(Foods)
                    }
                    onResult(FoodsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListFoodsAvailable(
    Foods: List<FoodResponseDTO>
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(Foods) { Foods ->
            Description(description = Foods.name)
        }
    }
}

@Composable
private fun FooterSelectFoods(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
    ) {
        SimpleButton(
            onClick = onDismissRequest,
            label = CANCEL,
            background = Themes.colors.error,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        SimpleButton(
            onClick = onConfirmation,
            label = CONFIRM,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindFoodByName(
    viewModel: FoodViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<FoodResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<FoodResponseDTO>> by remember { viewModel.findFoodByName }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllFoods()
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
