package br.com.digital.store.ui.view.reservation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.strings.StringsUtils.ADD_DIFFERENT_VALUES
import br.com.digital.store.components.strings.StringsUtils.FINAL_VALUE
import br.com.digital.store.components.strings.StringsUtils.GENERATE_RESERVATIONS
import br.com.digital.store.components.strings.StringsUtils.INITIAL_VALUE
import br.com.digital.store.components.strings.StringsUtils.INITIAL_VALUE_GREATER_THAN_ZERO
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.PREFIX
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.reservation.data.dto.GenerateReservationsRequestVO
import br.com.digital.store.features.reservation.ui.viewmodel.ReservationViewModel
import br.com.digital.store.features.reservation.ui.viewmodel.ResetReservation
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun GenerateReservations(
    viewModel: ReservationViewModel,
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var prefix: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        var start: Int by remember { mutableStateOf(value = NUMBER_ZERO) }
        var end: Int by remember { mutableStateOf(value = NUMBER_ZERO) }
        TextField(
            label = PREFIX,
            value = prefix,
            isError = observer.second,
            onValueChange = {
                prefix = it
            }
        )
        TextField(
            label = INITIAL_VALUE,
            value = start.toString(),
            keyboardType = KeyboardType.Number,
            isError = observer.second,
            onValueChange = {
                start = it.toIntOrNull() ?: NUMBER_ZERO
            }
        )
        TextField(
            label = FINAL_VALUE,
            value = end.toString(),
            keyboardType = KeyboardType.Number,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                end = it.toIntOrNull() ?: NUMBER_ZERO
            }
        )
        LoadingButton(
            label = GENERATE_RESERVATIONS,
            onClick = {
                observer = if (prefix.isEmpty() || start == NUMBER_ZERO || end == NUMBER_ZERO) {
                    Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                } else if (start == end) {
                    Triple(first = false, second = true, third = ADD_DIFFERENT_VALUES)
                } else if (start > end) {
                    Triple(first = false, second = true, third = INITIAL_VALUE_GREATER_THAN_ZERO)
                } else {
                    viewModel.generateReservations(
                        body = GenerateReservationsRequestVO(
                            prefix = prefix,
                            start = start,
                            end = end
                        )
                    )
                    Triple(first = true, second = false, third = EMPTY_TEXT)
                }
            },
            isEnabled = observer.first
        )
        ObserveNetworkStateHandlerGenerateReservations(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                prefix = EMPTY_TEXT
                start = NUMBER_ZERO
                end = NUMBER_ZERO
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerGenerateReservations(
    viewModel: ReservationViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.generateReservations }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetReservation(reset = ResetReservation.GENERATE_RESERVATIONS)
            onSuccessful()
        }
    )
}
