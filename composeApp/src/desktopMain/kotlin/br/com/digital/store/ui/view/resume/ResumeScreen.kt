package br.com.digital.store.ui.view.resume

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.RESUME
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.resume.ui.view.ResumeTabs
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun ResumeScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = RESUME,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            ResumeTabs(goToAlternativeRoutes = goToAlternativeRoutes)
        }
    )
}