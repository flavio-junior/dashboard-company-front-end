package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.PDV
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun PdvScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    BodyPage(
        body = {
            Row {
                Services(
                    label = PDV,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                PdvTabs(
                    goToNextScreen = goToNextScreen,
                    goToAlternativeRoutes = goToAlternativeRoutes
                )
            }
        }
    )
}
