package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.PDV
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun PdvScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
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
                CashRegisterScreen(
                    modifier = Modifier
                        .padding(
                            top = Themes.size.spaceSize16,
                            bottom = Themes.size.spaceSize16,
                            end = Themes.size.spaceSize16
                        )
                )
            }
        }
    )
}
