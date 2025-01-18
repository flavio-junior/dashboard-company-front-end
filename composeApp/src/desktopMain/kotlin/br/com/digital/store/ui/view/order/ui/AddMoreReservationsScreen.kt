package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_MORE_RESERVATIONS
import br.com.digital.store.components.strings.StringsUtils.ADD_RESERVATIONS
import br.com.digital.store.components.strings.StringsUtils.NOT_EMPTY_LIST_RESERVATIONS
import br.com.digital.store.components.ui.ButtonCreate
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.pdv.utils.PdvUtils.SELECT_RESERVATIONS_TO_ORDER
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import br.com.digital.store.features.reservation.ui.ui.SelectReservations
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.pdv.ListReservationsSelected
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun AddMoreReservationsScreen(
    orderId: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var addReservations: Boolean by remember { mutableStateOf(value = false) }
    val reservationsToSave = remember { mutableStateListOf<ReservationResponseDTO>() }
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        Description(description = SELECT_RESERVATIONS_TO_ORDER)
        ButtonCreate(
            label = ADD_MORE_RESERVATIONS,
            onItemSelected = {
                addReservations = true
            }
        )
        ListReservationsSelected(
            reservations = reservationsToSave,
            onItemSelected = { result ->
                if (reservationsToSave.contains(element = result)) {
                    reservationsToSave.remove(element = result)
                }
            }
        )
        LoadingButton(
            label = ADD_RESERVATIONS,
            onClick = {
                if (reservationsToSave.isEmpty()) {
                    observer = Triple(
                        first = false,
                        second = true,
                        third = NOT_EMPTY_LIST_RESERVATIONS
                    )
                } else {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.incrementMoreReservationsOrder(
                        orderId = orderId,
                        reservationsToSava = reservationsToSave
                    )
                }
            },
            isEnabled = observer.first
        )
        IsErrorMessage(isError = observer.second, message = observer.third)
        if (addReservations) {
            SelectReservations(
                onDismissRequest = {
                    addReservations = false
                },
                onConfirmation = { reservationsResult ->
                    reservationsResult.forEach { reservation ->
                        reservationsToSave.add(reservation)
                    }
                    addReservations = false
                }
            )
        }
    }
    ObserveNetworkStateHandlerIncrementMoreReservationsOrder(
        viewModel = viewModel,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onError = {
            observer = it
        },
        onSuccessful = {
            onRefresh()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerIncrementMoreReservationsOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.incrementMoreReservationsOrder }
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
            viewModel.resetOrder(reset = ResetOrder.INCREMENT_MORE_RESERVATIONS_ORDER)
            onSuccessful()
        }
    )
}
