package br.com.digital.store.ui.view.reservation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.strings.StringsUtils.ACTUAL_NAME
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.close
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.reservation.data.dto.EditReservationRequestDTO
import br.com.digital.store.features.reservation.data.vo.ReservationResponseVO
import br.com.digital.store.features.reservation.ui.viewmodel.ReservationViewModel
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.reservation.ReservationUtils.EDIT_RESERVATION
import br.com.digital.store.ui.view.reservation.ReservationUtils.NEW_NAME_RESERVATION
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.checkNameIsNull
import br.com.digital.store.utils.onBorder
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun EditReservation(
    modifier: Modifier = Modifier,
    reservationVO: ReservationResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onCleanReservations: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        val viewModel: ReservationViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var openDialog by remember { mutableStateOf(value = false) }
        var reservationsName by remember { mutableStateOf(value = EMPTY_TEXT) }
        val editReservations = { reservation: String ->
            if (checkNameIsNull(name = reservation)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.editReservation(
                    reservation = EditReservationRequestDTO(
                        id = reservationVO.id,
                        name = reservation
                    )
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = reservationVO.id.toString(),
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
            )
            TextField(
                enabled = false,
                label = ACTUAL_NAME,
                value = reservationVO.name ?: EMPTY_TEXT,
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2),
            )
            IconDefault(
                icon = Res.drawable.close, modifier = Modifier
                    .onBorder(
                        onClick = {},
                        color = Themes.colors.primary,
                        spaceSize = Themes.size.spaceSize16,
                        width = Themes.size.spaceSize2
                    )
                    .size(size = Themes.size.spaceSize64)
                    .padding(all = Themes.size.spaceSize8),
                onClick = onCleanReservations
            )
        }
        TextField(
            label = NEW_NAME_RESERVATION,
            value = reservationsName,
            icon = Res.drawable.edit,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                reservationsName = it
            },
            onGo = {
                openDialog = true
            }
        )
        LoadingButton(
            label = EDIT_RESERVATION,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        if (openDialog) {
            Alert(
                label = "$EDIT_RESERVATION?",
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    editReservations(reservationsName)
                    openDialog = false
                }
            )
        }
        ObserveNetworkStateHandlerEditReservations(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                onCleanReservations()
                reservationsName = EMPTY_TEXT
            }
        )
        DeleteReservation(
            viewModel = viewModel,
            id = reservationVO.id ?: 0,
            modifier = Modifier,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                onCleanReservations()
            }
        )
        GenerateReservations(
            viewModel = viewModel,
            modifier = Modifier,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerEditReservations(
    viewModel: ReservationViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.editReservation }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onSuccessful()
            viewModel.findAllReservations()
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
}
