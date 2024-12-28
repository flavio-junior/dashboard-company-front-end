package br.com.digital.store.ui.view.product

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.product.ui.view.ProductsTabs
import br.com.digital.store.features.product.utils.ProductUtils.PRODUCTS
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun ProductScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    BodyPage(
        body = {
            Row {
                Services(
                    label = PRODUCTS,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                ProductsTabs(goToAlternativeRoutes = goToAlternativeRoutes)
            }
        }
    )
}