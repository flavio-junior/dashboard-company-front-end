package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.REMOVER_RESERVATION
import br.com.digital.store.components.strings.StringsUtils.SELECTED_RESERVATIONS
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch

@Composable
fun ListReservationsSelected(
    modifier: Modifier = Modifier,
    reservations: List<ReservationResponseDTO>,
    onItemSelected: (ReservationResponseDTO) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        Description(description = SELECTED_RESERVATIONS)
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var selectedIndex by remember { mutableStateOf(value = -1) }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            state = scrollState,
            modifier = Modifier
                .background(color = Themes.colors.background)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    },
                )
                .padding(horizontal = Themes.size.spaceSize16)
        ) {
            itemsIndexed(items = reservations) { index, objectResult ->
                CardReservation(
                    reservation = objectResult,
                    selected = selectedIndex == index,
                    onItemSelected = onItemSelected,
                    onDisableItem = {
                        selectedIndex = index
                    }
                )
            }
        }
    }
}

@Composable
private fun CardReservation(
    reservation: ReservationResponseDTO,
    selected: Boolean = false,
    onItemSelected: (ReservationResponseDTO) -> Unit = {},
    onDisableItem: () -> Unit = {}
) {
    var openDialog by remember { mutableStateOf(value = false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .onBorder(
                onClick = {
                    openDialog = true
                    onDisableItem()
                },
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .background(color = if (selected) ITEM_SELECTED else Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .height(height = Themes.size.spaceSize40)
            .width(width = Themes.size.spaceSize100),
        verticalArrangement = Arrangement.Center
    ) {
        SimpleText(
            text = reservation.name,
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        if (openDialog) {
            Alert(
                label = REMOVER_RESERVATION,
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    onItemSelected(reservation)
                    openDialog = false
                }
            )
        }
    }
}
