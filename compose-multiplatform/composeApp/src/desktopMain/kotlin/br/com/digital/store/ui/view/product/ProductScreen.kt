package br.com.digital.store.ui.view.product

import androidx.compose.runtime.Composable
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.strings.StringsUtils.PRODUCTS
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun ProductScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        body = {
            Services(
                label = PRODUCTS,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    )
}
