package br.com.digital.store.ui.reservation

import androidx.compose.runtime.Composable
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.strings.StringsUtils.RESERVATION
import br.com.digital.store.ui.shared.BodyPage
import br.com.digital.store.ui.shared.Services

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
