package br.com.digital.store.features.reservation.ui.ui

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
import br.com.digital.store.components.strings.StringsUtils.CANCEL
import br.com.digital.store.components.strings.StringsUtils.CONFIRM
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Search
import br.com.digital.store.components.ui.SimpleButton
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import br.com.digital.store.features.reservation.ui.viewmodel.ReservationViewModel
import br.com.digital.store.features.reservation.ui.viewmodel.ResetReservation
import br.com.digital.store.features.reservation.utils.ReservationUtils.ADD_RESERVATIONS
import br.com.digital.store.features.reservation.utils.ReservationUtils.NO_RESERVATIONS_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SelectReservations(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (List<ReservationResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: ReservationViewModel = getKoin().get()
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
            val reservations = remember { mutableStateListOf<ReservationResponseDTO>() }
            var reservationsSelected = remember { mutableStateListOf<ReservationResponseDTO>() }
            Title(title = ADD_RESERVATIONS)
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findReservationByName(name = name)
                }
            )
            SelectReservations(
                reservations = reservations,
                reservationsSelected = reservationsSelected,
                onResult = {
                    reservationsSelected = it.toMutableStateList()
                }
            )
            ListReservationsAvailable(reservations = reservationsSelected)
            FooterSelectReservations(
                onDismissRequest = {
                    viewModel.resetReservation(reset = ResetReservation.FIND_RESERVATION_BY_NAME)
                    onDismissRequest()
                },
                onConfirmation = {
                    if (reservationsSelected.isNotEmpty()) {
                        viewModel.resetReservation(reset = ResetReservation.FIND_RESERVATION_BY_NAME)
                        onConfirmation(reservationsSelected)
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_RESERVATIONS_SELECTED)
                    }
                }
            )
            ObserveNetworkStateHandlerFindReservationByName(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    reservations.clear()
                    reservations.addAll(it)
                }
            )
        }
    }
}

@Composable
private fun SelectReservations(
    reservations: List<ReservationResponseDTO>,
    reservationsSelected: MutableList<ReservationResponseDTO>,
    onResult: (List<ReservationResponseDTO>) -> Unit = {}
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
        items(reservations) { reservations ->
            Tag(
                text = reservations.name,
                value = reservations,
                enabled = reservationsSelected.contains(reservations),
                onCheck = { isChecked ->
                    if (isChecked) {
                        reservationsSelected.add(reservations)
                    } else {
                        reservationsSelected.remove(reservations)
                    }
                    onResult(reservationsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListReservationsAvailable(
    reservations: List<ReservationResponseDTO>
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
        items(reservations) { reservation ->
            Description(description = reservation.name)
        }
    }
}

@Composable
private fun FooterSelectReservations(
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
private fun ObserveNetworkStateHandlerFindReservationByName(
    viewModel: ReservationViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<ReservationResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<ReservationResponseDTO>> by remember { viewModel.findReservationByName }
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
            viewModel.findAllReservations()
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
