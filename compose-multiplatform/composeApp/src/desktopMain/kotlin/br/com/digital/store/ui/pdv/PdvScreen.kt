package br.com.digital.store.ui.pdv

import androidx.compose.runtime.Composable
import br.com.digital.store.components.ui.Description
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.strings.StringsUtils.PDV
import br.com.digital.store.ui.shared.BodyPage
import br.com.digital.store.ui.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun PdvScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = PDV,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            Description(description = "Descrição")
        }
    )
}
