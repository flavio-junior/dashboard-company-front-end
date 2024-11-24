package br.com.digital.store.ui.view.reservation

import androidx.compose.runtime.Composable
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.strings.StringsUtils.RESERVATION
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun ReservationScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        body = {
            Services(
                label = RESERVATION,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    )
}
