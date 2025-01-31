package br.com.digital.store.ui.view.fee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.fee.data.vo.FeeResponseVO
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.navigation.ItemNavigation
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ListFeeScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
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
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccess = {
                goToNextScreen(ItemNavigation.FEE.name)
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerFees(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<FeeResponseVO>> by remember { viewModel.findAllFees }
    var feeId: Long by remember { mutableStateOf(value = 0) }
    var isChecked: Boolean by remember { mutableStateOf(value = false) }
    var feeResponseVO by remember { mutableStateOf(value = FeeResponseVO()) }
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
            if (it.result?.isNotEmpty() == true) {
                ListFees(
                    viewModel = viewModel,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    fees = it.result,
                    onItemSelected = { itemSelected ->
                        feeResponseVO = itemSelected
                    },
                    registerDays = { idSaved ->
                        feeId = idSaved
                        if (feeId > NUMBER_ZERO) {
                            isChecked = true
                        }
                    },
                    onSuccess = onSuccess
                )
                AvailableDaysFee(
                    feeId = feeResponseVO.id,
                    viewModel = viewModel,
                    dayOfWeek = feeResponseVO.days,
                    onClick = {
                        isChecked = true
                    },
                    onSuccess = onSuccess
                )
                AddDaysOkWeek(
                    id = feeResponseVO.id,
                    viewModel = viewModel,
                    enabled = isChecked,
                    onSuccess = {
                        isChecked = false
                        onSuccess()
                    }
                )
               if (feeResponseVO.id > NUMBER_ZERO) {
                UpdatePriceFee(
                    feeId = feeResponseVO.id,
                    viewModel = viewModel,
                    onSuccess = onSuccess
                )
                   }
            } else {
                CreateNewFee(viewModel = viewModel, onSuccess = onSuccess)
            }
        }
    )
}
