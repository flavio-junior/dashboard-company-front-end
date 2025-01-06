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
import br.com.digital.store.components.strings.StringsUtils.NAME_AUTHOR
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.RESUME
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.report.data.dto.ReportRequestDTO
import br.com.digital.store.features.report.ui.viewmodel.ReportViewModel
import br.com.digital.store.features.report.ui.viewmodel.ResetReport
import br.com.digital.store.features.report.utils.ReportUtils.CREATE_NEW_REPORT
import br.com.digital.store.features.report.utils.ReportUtils.MAX_LIMIT_RESUME
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CreateNewReportScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: ReportViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var resume: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var nameAuthor: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
            .padding(
                top = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16
            )
    ) {
        TextField(
            label = RESUME,
            value = resume,
            singleLine = false,
            isError = observer.second,
            onValueChange = {
                if (it.length < 1000) {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    resume = it
                } else {
                    observer = Triple(first = false, second = true, third = MAX_LIMIT_RESUME)
                }
            }
        )
        Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
            TextField(
                label = NAME_AUTHOR,
                value = nameAuthor,
                isError = observer.second,
                onValueChange = {
                    nameAuthor = it
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            LoadingButton(
                label = CREATE_NEW_REPORT,
                onClick = {
                    if (resume.isNotEmpty() && nameAuthor.isNotEmpty()) {
                        viewModel.createReport(
                            report = ReportRequestDTO(resume = resume, author = nameAuthor)
                        )
                    } else {
                        observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                    }
                },
                isEnabled = observer.first,
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
        }
        IsErrorMessage(isError = observer.second, message = observer.third)
        ObserveNetworkStateHandlerCreateReport(
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
private fun ObserveNetworkStateHandlerCreateReport(
    viewModel: ReportViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createReport }
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
            viewModel.resetReport(reset = ResetReport.CREATE_REPORT)
            onSuccessful()
        }
    )
}
