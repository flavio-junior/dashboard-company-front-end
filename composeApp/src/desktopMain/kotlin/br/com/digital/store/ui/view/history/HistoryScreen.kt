package br.com.digital.store.ui.view.history

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.REPORT
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun HistoryScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        body = {
            Services(
                label = REPORT,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    )
}