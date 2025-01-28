package br.com.digital.store.ui.view.user

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.SETTINGS
import br.com.digital.store.domain.factory.settings
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun UserConfigScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = SETTINGS,
                services = settings,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    )
}
