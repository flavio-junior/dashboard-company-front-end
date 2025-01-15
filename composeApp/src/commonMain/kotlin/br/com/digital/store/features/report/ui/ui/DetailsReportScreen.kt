package br.com.digital.store.features.report.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.AUTHOR
import br.com.digital.store.components.strings.StringsUtils.DATE
import br.com.digital.store.components.strings.StringsUtils.HOUR
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.RESUME
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Button
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.ResourceUnavailable
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.print
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.report.data.vo.ReportResponseVO
import br.com.digital.store.features.report.ui.viewmodel.ReportViewModel
import br.com.digital.store.features.report.ui.viewmodel.ResetReport
import br.com.digital.store.features.report.utils.ReportUtils.DELETE_REPORT
import br.com.digital.store.features.report.utils.ReportUtils.DETAILS_REPORT
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun DetailsReportScreen(
    report: ReportResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    if (report.id > NUMBER_ZERO) {
        HeaderDetailsReport(
            report = report,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
    } else {
        ResourceUnavailable()
    }
}

@Composable
fun HeaderDetailsReport(
    report: ReportResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        val viewModel: ReportViewModel = getKoin().get()
        var openDialog: Boolean by remember { mutableStateOf(value = false) }
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        Description(description = "$DETAILS_REPORT:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = report.id.toString(),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = DATE,
                value = report.date ?: EMPTY_TEXT,
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = HOUR,
                value = report.hour ?: EMPTY_TEXT,
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = AUTHOR,
                value = report.author ?: EMPTY_TEXT,
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            LoadingButton(
                label = DELETE_REPORT,
                onClick = {
                    openDialog = true
                },
                isEnabled = observer.first,
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            Button(icon = Res.drawable.print)
        }
        TextField(
            enabled = false,
            label = RESUME,
            value = report.resume ?: EMPTY_TEXT,
            singleLine = false,
            onValueChange = {}
        )
        IsErrorMessage(isError = observer.second, message = observer.third)
        if (openDialog) {
            Alert(
                label = "$DELETE_REPORT?",
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.deleteReport(reportId = report.id)
                    openDialog = false
                }
            )
        }
        ObserveNetworkStateHandlerDeleteReport(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                onRefresh()
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerDeleteReport(
    viewModel: ReportViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteReport }
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
            viewModel.resetReport(reset = ResetReport.DELETE_REPORT)
            onSuccessful()
        }
    )
}
