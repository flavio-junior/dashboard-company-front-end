package br.com.digital.store.features.resume.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.utils.NumbersUtils

@Composable
fun ResumeTabMain(
    index: Int,
    pierChart: PierChart = PierChart(),
    onItemSelected: (Pair<PierChart, Int>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> GetDetailsAnaliseScreen(
            onItemSelected = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes
        )

        NumbersUtils.NUMBER_ONE -> ResumeShoppingCatScreen(pierChart = pierChart)

        NumbersUtils.NUMBER_TWO -> ResumeDeliveryScreen(pierChart = pierChart)

        NumbersUtils.NUMBER_THREE -> ResumePickupScreen(pierChart = pierChart)

        NumbersUtils.NUMBER_FOUR -> ResumeReservationScreen(pierChart = pierChart)
    }
}
