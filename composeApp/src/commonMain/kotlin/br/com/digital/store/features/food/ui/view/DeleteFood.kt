package br.com.digital.store.features.food.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.food.ui.viewmodel.FoodViewModel
import br.com.digital.store.features.food.ui.viewmodel.ResetFood
import br.com.digital.store.features.food.utils.FoodUtils.DELETE_FOOD
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DeleteFood(
    modifier: Modifier = Modifier,
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit
) {
    val viewModel: FoodViewModel = getKoin().get()
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    LoadingButton(
        onClick = {
            openDialog = true
        },
        label = DELETE_FOOD,
        isEnabled = observer.first,
        modifier = modifier
    )
    if (openDialog) {
        Alert(
            label = "$DELETE_FOOD?",
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.deleteFood(id = id)
                openDialog = false
            }
        )
    }
    ObserveNetworkStateHandlerDeleteFood(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            onRefresh()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerDeleteFood(
    viewModel: FoodViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteFood }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetFood(reset = ResetFood.DELETE_FOOD)
            viewModel.findAllFoods()
            onSuccessful()
        }
    )
}
