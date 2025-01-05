package br.com.digital.store.features.report.ui.ui

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.report.data.vo.ReportResponseVO
import br.com.digital.store.utils.NumbersUtils

@Composable
fun ReportTabMain(
    index: Int,
    report: ReportResponseVO = ReportResponseVO(),
    onItemSelected: (ReportResponseVO) -> Unit = {},
    onToCreateNewReport: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> ListReportsScreen(
            onItemSelected = onItemSelected,
            onToCreateNewReport = onToCreateNewReport,
            goToAlternativeRoutes = goToAlternativeRoutes
        )

        NumbersUtils.NUMBER_ONE -> DetailsReportScreen(
            report = report,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_ONE)
            }
        )

        NumbersUtils.NUMBER_TWO -> CreateNewReportScreen()
    }
}
