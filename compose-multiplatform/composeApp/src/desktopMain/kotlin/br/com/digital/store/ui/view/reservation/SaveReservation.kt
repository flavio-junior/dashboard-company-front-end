package br.com.digital.store.ui.view.reservation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.reservation.viewmodel.ReservationViewModel
import br.com.digital.store.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.reservation.ReservationUtils.RESERVATION_NAME
import br.com.digital.store.ui.view.reservation.ReservationUtils.SAVE_RESERVATION
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.checkNameIsNull
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SaveReservation(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val viewModel: ReservationViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var reservationName by remember { mutableStateOf(value = EMPTY_TEXT) }
        val saveReservation = { reservation: String ->
            if (checkNameIsNull(name = reservation)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.createReservation(reservation = reservation)
            }
        }
        TextField(
            label = RESERVATION_NAME,
            value = reservationName,
            icon = Res.drawable.edit,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = { reservationName = it },
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            onGo = {
                saveReservation(reservationName)
            }
        )
        LoadingButton(
            label = SAVE_RESERVATION,
            onClick = {
                saveReservation(reservationName)
            },
            isEnabled = observer.first,
            modifier = modifier.weight(weight = WEIGHT_SIZE)
        )
        ObserveNetworkStateHandlerCreateNewReservation(
            viewModel = viewModel,
            onError = {
                observer = it
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewReservation(
    viewModel: ReservationViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewReservation }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllReservations()
        }
    )
}
