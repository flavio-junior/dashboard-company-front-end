package br.com.digital.store.ui.view.fee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_FEE
import br.com.digital.store.components.strings.StringsUtils.CREATE_NEW_FEE
import br.com.digital.store.components.strings.StringsUtils.VALUE_FEE
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Price
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.fee.data.dto.CreateFeeRequestDTO
import br.com.digital.store.features.fee.domain.type.Function
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.order.ui.ItemObject
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.NUMBER_EQUALS_ZERO
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.ZERO_DOUBLE

@Composable
fun CreateNewFee(
    viewModel: FeeViewModel,
    onSuccess: () -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = Modifier.background(color = Themes.colors.background)
    ) {
        Title(title = CREATE_NEW_FEE)
        ItemObject {
            var percentage: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
            Price(
                label = VALUE_FEE,
                value = percentage,
                isError = observer.first,
                message = observer.third,
                onValueChange = {
                    percentage = it
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            LoadingButton(
                label = ADD_FEE,
                onClick = {
                    if (percentage != ZERO_DOUBLE) {
                        observer = Triple(first = false, second = true, third = EMPTY_TEXT)
                        viewModel.createNewFee(
                            fee = CreateFeeRequestDTO(
                                percentage = percentage.toInt(),
                                assigned = Function.WAITER
                            )
                        )
                    } else {
                        observer = Triple(first = true, second = false, third = NUMBER_EQUALS_ZERO)
                    }
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
                isEnabled = observer.second
            )
        }
    }
    ObserveNetworkStateHandlerCreateNewFee(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        onSuccess = onSuccess
    )
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewFee(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewFee }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = true, second = false, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccess()
        }
    )
}
