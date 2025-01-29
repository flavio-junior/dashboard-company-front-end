package br.com.digital.store.ui.view.fee

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.FEES
import br.com.digital.store.domain.factory.settings
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun FeeScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = FEES,
                services = settings,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            ListFeeScreen(
                goToAlternativeRoutes = goToAlternativeRoutes,
                goToNextScreen = goToNextScreen
            )
        }
    )
}
