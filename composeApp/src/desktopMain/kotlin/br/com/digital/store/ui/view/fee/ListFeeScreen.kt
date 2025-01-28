package br.com.digital.store.ui.view.fee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.DAYS_OF_FEES
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.features.fee.data.vo.FeeResponseVO
import br.com.digital.store.features.fee.domain.factory.dayFactory
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ListFeeScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: FeeViewModel = getKoin().get()
    LaunchedEffect(key1 = Unit) {
        viewModel.findAllFees()
    }
    Column(
        modifier = Modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        ObserveNetworkStateHandlerFees(
            viewModel = viewModel,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerFees(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<FeeResponseVO>> by remember { viewModel.findAllFees }
    var feeId: Long by remember { mutableStateOf(value = 0) }
    var isChecked: Boolean by remember { mutableStateOf(value = false) }
    var dayOfWeek by remember { mutableStateOf(value = FeeResponseVO()) }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            ListFees(
                fees = it.result ?: emptyList(),
                onItemSelected = { itemSelected ->
                    dayOfWeek = itemSelected
                },
                registerDays = { idSaved ->
                    feeId = idSaved
                    if (feeId > NumbersUtils.NUMBER_ZERO) {
                        isChecked = true
                    }
                }
            )
            AddDaysOkWeek(
                id = feeId,
                viewModel = viewModel,
                enabled = isChecked,
                onSuccess = {
                    isChecked = false
                }
            )
            if (dayOfWeek.days != null) {
                Description(description = DAYS_OF_FEES)
                Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
                    dayOfWeek.days?.forEach { day ->
                        Tag(
                            text = dayFactory(day = day.day),
                            value = day
                        )
                    }
                }
            }
        }
    )
}
