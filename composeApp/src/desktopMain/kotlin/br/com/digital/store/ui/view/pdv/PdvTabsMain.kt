package br.com.digital.store.ui.view.pdv

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.utils.NumbersUtils

@Composable
fun PdvTabsMain(
    index: Int,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    goToNextTab: (Int) -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    when(index) {
        NumbersUtils.NUMBER_ZERO -> SelectTypeOrderScreen(goToNextTab = goToNextTab)

        NumbersUtils.NUMBER_ONE -> ShoppingCartScreen(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_ONE)
            }
        )

        NumbersUtils.NUMBER_TWO -> CreateDeliveryOrderScreen(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_TWO)
            }
        )

        NumbersUtils.NUMBER_THREE -> CreateOrderScreen(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_THREE)
            }
        )

        NumbersUtils.NUMBER_FOUR -> CreateReservationOrderScreen(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_FOUR)
            }
        )
    }
}
