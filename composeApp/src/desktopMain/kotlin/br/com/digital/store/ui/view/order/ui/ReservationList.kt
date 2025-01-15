package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.components.strings.StringsUtils.RESERVATIONS
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.utils.OrderUtils.TOTAL_NUMBER_RESERVATIONS
import br.com.digital.store.theme.Themes
import kotlinx.coroutines.launch

@Composable
fun ReservationList(
    modifier: Modifier = Modifier,
    orderResponseVO: List<OrderResponseVO>,
    onItemSelected: (Pair<OrderResponseVO, Int>) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = Themes.size.spaceSize16)
            .fillMaxHeight()
    ) {
        Title(title = RESERVATIONS, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        NumberTotalReservations(label = TOTAL_NUMBER_RESERVATIONS, size = orderResponseVO.size)
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize16))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            state = scrollState,
            modifier = modifier
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(delta)
                        }
                    },
                )
        ) {
            items(orderResponseVO) { order ->
                CardReservation(
                    orderResponseVO = order,
                    onItemSelected = onItemSelected
                )
            }
        }
    }
}
