package br.com.digital.store.ui.view.fee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.NO_SELECTED_DAYS_OF_WEEK
import br.com.digital.store.components.strings.StringsUtils.SAVE_DAYS_OF_WEEK
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.features.fee.data.dto.DayRequestDTO
import br.com.digital.store.features.fee.domain.day.DayOfWeek
import br.com.digital.store.features.fee.domain.factory.daysOfWeek
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

@Composable
fun AddDaysOkWeek(
    id: Long,
    viewModel: FeeViewModel,
    enabled: Boolean = false,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val daysSelected: MutableList<DayOfWeek> = remember { mutableStateListOf() }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    if (enabled) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfWeek.forEach { day ->
                Tag(
                    text = day.title,
                    value = day.dayOfWeek,
                    enabled = daysSelected.contains(element = day.dayOfWeek),
                    onCheck = { isChecked ->
                        if (isChecked) {
                            if (!daysSelected.contains(element = day.dayOfWeek)) {
                                daysSelected.add(element = day.dayOfWeek)
                            }
                        } else {
                            daysSelected.remove(element = day.dayOfWeek)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(width = Themes.size.spaceSize16))
            }
        }
        LoadingButton(
            label = SAVE_DAYS_OF_WEEK,
            onClick = {
                if (daysSelected.isNotEmpty()) {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.addDaysOkWeek(
                        id = id,
                        daysOfWeek = DayRequestDTO(days = daysSelected)
                    )
                } else {
                    observer =
                        Triple(first = false, second = true, third = NO_SELECTED_DAYS_OF_WEEK)
                }
            },
            isEnabled = observer.first
        )
    }
    IsErrorMessage(isError = observer.second, observer.third)
    ObserveNetworkStateHandlerAddDaysOkWeek(
        viewModel = viewModel,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onError = {
            observer = it
        },
        onSuccess = {
            daysSelected.clear()
            onSuccess()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerAddDaysOkWeek(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.addDaysOkWeek }
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
            viewModel.resetFee()
            onSuccess()
        }
    )
}
