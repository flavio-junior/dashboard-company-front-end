package br.com.digital.store.ui.view.food

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.FOODS
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.features.food.ui.view.FoodTabs
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services

@Composable
fun FoodScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    BodyPage(
        body = {
            Row {
                Services(
                    label = FOODS,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                FoodTabs(goToAlternativeRoutes = goToAlternativeRoutes)
            }
        }
    )
}
