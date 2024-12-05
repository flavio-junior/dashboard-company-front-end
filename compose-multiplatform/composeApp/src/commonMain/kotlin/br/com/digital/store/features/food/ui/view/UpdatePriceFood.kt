package br.com.digital.store.features.food.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CONFIRM_UPDATE
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.food.data.dto.UpdatePriceFoodRequestDTO
import br.com.digital.store.features.food.ui.viewmodel.FoodViewModel
import br.com.digital.store.features.food.utils.FoodUtils.UPDATE_PRICE_FOOD
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.checkPriceIsNull
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdatePriceFood(
    modifier: Modifier = Modifier,
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: FoodViewModel = getKoin().get()
    var price: String by remember { mutableStateOf(value = "0.0") }
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val checkUpdatePriceFood = {
        if (checkPriceIsNull(price = price.toDouble())
        ) {
            observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
        } else {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updatePriceFood(
                id = id,
                price = UpdatePriceFoodRequestDTO(
                    price = price.toDouble()
                )
            )
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = "$UPDATE_PRICE_FOOD:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = modifier,
            verticalAlignment = Alignment.Top
        ) {
            TextField(
                label = PRICE,
                value = price,
                isError = observer.second,
                message = observer.third,
                onValueChange = {
                    price = it
                },
                modifier = modifier.weight(weight = CommonUtils.WEIGHT_SIZE),
                onGo = {
                    openDialog = true
                }
            )
            LoadingButton(
                onClick = {
                    openDialog = true
                },
                label = CONFIRM_UPDATE,
                isEnabled = observer.first,
                modifier = modifier.weight(weight = CommonUtils.WEIGHT_SIZE)
            )
        }
        if (openDialog) {
            Alert(
                label = "$UPDATE_PRICE_FOOD?",
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    checkUpdatePriceFood()
                    openDialog = false
                }
            )
        }
        ObserveNetworkStateHandlerUpdatePriceFood(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                price = "0.0"
                onRefresh()
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerUpdatePriceFood(
    viewModel: FoodViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updatePriceFood }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccessful()
        }
    )
}
