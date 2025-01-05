package br.com.digital.store.features.report.ui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.EmptyList
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.receipt
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.report.data.vo.ReportResponseVO
import br.com.digital.store.features.report.data.vo.ReportsResponseVO
import br.com.digital.store.features.report.ui.viewmodel.ReportViewModel
import br.com.digital.store.features.report.utils.ReportUtils.EMPTY_LIST_REPORTS
import br.com.digital.store.features.report.utils.ReportUtils.NONE_REPORTS
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ListReportsScreen(
    onItemSelected: (ReportResponseVO) -> Unit = {},
    onToCreateNewReport: () -> Unit = {},
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
        HeaderReports(
            onSort = { size, sort, route ->
                viewModel.findAllReports(size = size, sort = sort, route = route)
            },
            onFilter = { size, sort, route ->
                viewModel.findAllReports(size = size, sort = sort, route = route)
            },
            onRefresh = { size, sort, route ->
                viewModel.findAllReports(size = size, sort = sort, route = route)
            },
            onCreateNewReport = onToCreateNewReport
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
    onItemSelected: (ReportResponseVO) -> Unit = {},
    goToOrderScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<ReportsResponseVO> by remember { viewModel.findAllReports }
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
                    title = EMPTY_LIST_REPORTS,
                    description = NONE_REPORTS,
                    mainIcon = Res.drawable.receipt,
                    onClick = goToOrderScreen,
                    refresh = {
                        viewModel.findAllReports()
                    }
                )
            } else {
                it.result?.let { response ->
                    ReportsResult(reportsResponseVO = response, onItemSelected = onItemSelected)
                } ?: viewModel.showEmptyList(show = true)
            }
        }
    )
}

@Composable
private fun ReportsResult(
    reportsResponseVO: ReportsResponseVO,
    onItemSelected: (ReportResponseVO) -> Unit = {}
) {
    Column {
        ListReports(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize12),
            checkouts = reportsResponseVO,
            onItemSelected = onItemSelected
        )
        PageIndicatorReports(content = reportsResponseVO)
    }
}
