package br.com.digital.store.ui.view.report

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.REPORTS
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.report.ui.ui.ReportTabs
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun ReportScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = REPORTS,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            ReportTabs(goToAlternativeRoutes = goToAlternativeRoutes)
        }
    )
}
