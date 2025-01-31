package br.com.digital.store.ui.view.fee

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_DAYS
import br.com.digital.store.components.strings.StringsUtils.DAYS_OF_FEES
import br.com.digital.store.components.strings.StringsUtils.DELETE_DAY_FEE
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.features.fee.data.vo.DayResponseVO
import br.com.digital.store.features.fee.domain.day.DayOfWeek
import br.com.digital.store.features.fee.domain.factory.dayFactory
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import br.com.digital.store.features.fee.ui.viewmodel.ResetFee
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import kotlinx.coroutines.launch

@Composable
fun AvailableDaysFee(
    feeId: Long,
    viewModel: FeeViewModel,
    dayOfWeek: List<DayResponseVO>? = null,
    onClick: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    var deleteDay: Boolean by remember { mutableStateOf(value = false) }
    var dayId: Long? by remember { mutableStateOf(value = null) }
    if (!dayOfWeek.isNullOrEmpty()) {
        Description(description = DAYS_OF_FEES)
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = CommonUtils.WEIGHT_SIZE_3)
            ) {
                val scrollState = rememberLazyListState()
                val coroutineScope = rememberCoroutineScope()
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                    state = scrollState,
                    modifier = Modifier
                        .background(color = Themes.colors.background)
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                coroutineScope.launch {
                                    scrollState.scrollBy(value = -delta)
                                }
                            },
                        )
                ) {
                    items(items = dayOfWeek) { day ->
                        Tag(
                            text = dayFactory(day = day.day),
                            value = day,
                            onCheck = {
                                if (it) {
                                    dayId = day.id
                                    deleteDay = true
                                }
                            }
                        )
                    }
                }
            }
            if (!dayOfWeek.any { it.day == DayOfWeek.ALL }) {
                LoadingButton(
                    label = ADD_DAYS,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    onClick = onClick
                )
            }
        }
    }
    if (deleteDay) {
        Alert(
            label = DELETE_DAY_FEE,
            onDismissRequest = {
                deleteDay = false
            },
            onConfirmation = {
                viewModel.deleteDayFee(feeId = feeId, dayId = dayId ?: 0)
                deleteDay = false
            }
        )
    }
    ObserveNetworkStateHandlerDeleteDayFee(
        viewModel = viewModel,
        onError = {},
        onSuccess = onSuccess
    )
}

@Composable
private fun ObserveNetworkStateHandlerDeleteDayFee(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteDayOFee }
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
            viewModel.resetFee(reset = ResetFee.DELETE_DAY_OF_FEE)
            onSuccess()
        }
    )
}
