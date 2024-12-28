package br.com.digital.store.ui.view.order

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.ORDER
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.order.ui.view.OrdersTabs
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun OrderScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    BodyPage(
        body = {
            Row {
                Services(
                    label = ORDER,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                OrdersTabs(goToAlternativeRoutes = goToAlternativeRoutes)
            }
        }
    )
}
