package br.com.digital.store.ui.view.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.EmptyList
import br.com.digital.store.components.ui.HeaderSearch
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.receipt
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.report.data.vo.PaymentResponseVO
import br.com.digital.store.features.report.data.vo.PaymentsResponseVO
import br.com.digital.store.features.report.ui.viewmodel.ReportViewModel
import br.com.digital.store.features.report.utils.ReportUtils.EMPTY_LIST_PAYMENTS
import br.com.digital.store.features.report.utils.ReportUtils.NONE_PAYMENT
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ListReportsScreen(
    onItemSelected: (PaymentResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    goToOrderScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
            .fillMaxSize()
    ) {
        val viewModel: ReportViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllReports()
        }
        HeaderSearch(
            onSearch = { _, size, sort, route ->
                viewModel.findAllReports(size = size, sort = sort, route = route)
            },
            onSort = { _, size, sort, route ->
                viewModel.findAllReports(size = size, sort = sort, route = route)
            },
            onFilter = { _, size, sort, route ->
                viewModel.findAllReports(size = size, sort = sort, route = route)
            },
            onRefresh = { _, size, sort, route ->
                viewModel.findAllReports(size = size, sort = sort, route = route)
            }
        )
        ObserveNetworkStateHandlerReports(
            viewModel = viewModel,
            onItemSelected = onItemSelected,
            goToOrderScreen = goToOrderScreen,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerReports(
    viewModel: ReportViewModel,
    onItemSelected: (PaymentResponseVO) -> Unit = {},
    goToOrderScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<PaymentsResponseVO> by remember { viewModel.findAllReports }
    val showEmptyList: Boolean by remember { viewModel.showEmptyList }
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
            if (showEmptyList) {
                EmptyList(
                    title = EMPTY_LIST_PAYMENTS,
                    description = NONE_PAYMENT,
                    mainIcon = Res.drawable.receipt,
                    onClick = goToOrderScreen,
                    refresh = {
                        viewModel.findAllReports()
                    }
                )
            } else {
                it.result?.let { response ->
                    ReportsResult(paymentsResponseVO = response, onItemSelected = onItemSelected)
                } ?: viewModel.showEmptyList(show = true)
            }
        }
    )
}

@Composable
private fun ReportsResult(
    paymentsResponseVO: PaymentsResponseVO,
    onItemSelected: (PaymentResponseVO) -> Unit = {}
) {
    Column {
        ListReports(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize12),
            checkouts = paymentsResponseVO,
            onItemSelected = onItemSelected
        )
        PageIndicatorReports(content = paymentsResponseVO)
    }
}
