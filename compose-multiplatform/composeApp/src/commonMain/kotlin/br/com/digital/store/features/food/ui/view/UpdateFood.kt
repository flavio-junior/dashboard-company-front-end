package br.com.digital.store.features.food.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CONFIRM_UPDATE
import br.com.digital.store.components.strings.StringsUtils.MESSAGE_ZERO_DOUBLE
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Price
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import br.com.digital.store.features.category.ui.view.SelectCategories
import br.com.digital.store.features.category.utils.CategoryUtils.ADD_CATEGORIES
import br.com.digital.store.features.category.utils.CategoryUtils.NO_CATEGORIES_SELECTED
import br.com.digital.store.features.food.data.dto.UpdateFoodRequestDTO
import br.com.digital.store.features.food.ui.viewmodel.FoodViewModel
import br.com.digital.store.features.food.utils.FoodUtils.NEW_NAME_FOOD
import br.com.digital.store.features.food.utils.FoodUtils.NEW_PRICE_FOOD
import br.com.digital.store.features.food.utils.checkBodyFoodIsNull
import br.com.digital.store.features.item.utils.ItemsUtils.checkPriceIsEqualsZero
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.ZERO_DOUBLE
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateFood(
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit
) {
    val viewModel: FoodViewModel = getKoin().get()
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    val selectedCategories = remember { mutableStateListOf<CategoryResponseDTO>() }
    var price: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
    var cleanText:Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var openDialog by remember { mutableStateOf(value = false) }
    var confirmUpdateFood by remember { mutableStateOf(value = false) }
    val checkUpdateFood = {
        if (checkBodyFoodIsNull(name = name, price = price.toDouble())
        ) {
            observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
        } else if (checkPriceIsEqualsZero(price = price.toDouble())) {
            observer = Triple(first = false, second = true, third = MESSAGE_ZERO_DOUBLE)
        } else if (selectedCategories.isEmpty()) {
            observer = Triple(first = false, second = true, third = NO_CATEGORIES_SELECTED)
        } else {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updateFood(
                food = UpdateFoodRequestDTO(
                    id = id,
                    name = name,
                    categories = selectedCategories,
                    price = price.toDouble()
                )
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        TextField(
            label = NEW_NAME_FOOD,
            value = name,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                name = it
            },
            modifier = Modifier.weight(weight = CommonUtils.WEIGHT_SIZE_2)
        )
        Price(
            value = price,
            label = NEW_PRICE_FOOD,
            isError = observer.second,
            cleanText = cleanText,
            onCleanText = {
                cleanText = it
            },
            onValueChange = {
                price = it
            },
            onGo = {
                openDialog = true
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        LoadingButton(
            onClick = {
                openDialog = true
            },
            label = ADD_CATEGORIES,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            onClick = {
                confirmUpdateFood = true
            },
            label = CONFIRM_UPDATE,
            isEnabled = observer.first,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        DeleteFood(
            id = id,
            goToAlternativeRoutes = goToAlternativeRoutes,
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            onRefresh = onRefresh
        )
    }
    ObserveNetworkStateHandlerUpdateFood(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            name = EMPTY_TEXT
            price = ZERO_DOUBLE
            cleanText = true
            onRefresh()
        }
    )
    if (openDialog) {
        SelectCategories(
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                selectedCategories.addAll(it)
                openDialog = false
            }
        )
    }
    if (confirmUpdateFood) {
        Alert(
            label = "$CONFIRM_UPDATE?",
            onDismissRequest = {
                confirmUpdateFood = false
            },
            onConfirmation = {
                checkUpdateFood()
                confirmUpdateFood = false
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerUpdateFood(
    viewModel: FoodViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateFood }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it.orEmpty()))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccessful()
        }
    )
}
