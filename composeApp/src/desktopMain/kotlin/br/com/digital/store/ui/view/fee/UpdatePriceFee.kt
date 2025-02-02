package br.com.digital.store.ui.view.fee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.strings.StringsUtils.NEW_VALUE_FEE
import br.com.digital.store.components.strings.StringsUtils.SEND_REQUEST_UPDATE_PRICE_FEE
import br.com.digital.store.components.strings.StringsUtils.UPDATE_PERCENTAGE_FEE
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.fee.data.dto.UpdatePriceFeeRequestDTO
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import br.com.digital.store.features.fee.ui.viewmodel.ResetFee
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.order.ui.ItemObject
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.NUMBER_EQUALS_ZERO
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun UpdatePercentageFee(
    feeId: Long,
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
        Title(title = UPDATE_PERCENTAGE_FEE)
        ItemObject {
            var percentage:Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
            TextField(
                label = NEW_VALUE_FEE,
                value = percentage.toString(),
                isError = observer.second,
                message = observer.third,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    percentage = it.toIntOrNull() ?: NUMBER_ZERO
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            LoadingButton(
                label = SEND_REQUEST_UPDATE_PRICE_FEE,
                onClick = {
                    if (percentage != NUMBER_ZERO) {
                        observer = Triple(first = false, second = true, third = EMPTY_TEXT)
                        viewModel.updatePriceFee(
                            feeId = feeId,
                            price = UpdatePriceFeeRequestDTO(
                                percentage = percentage
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
    ObserveNetworkStateHandlerUpdatePriceFee(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        onSuccess = onSuccess
    )
}

@Composable
private fun ObserveNetworkStateHandlerUpdatePriceFee(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updatePriceFee }
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
            viewModel.resetFee(reset = ResetFee.UPDATE_PRICE_FEE)
            onSuccess()
        }
    )
}
