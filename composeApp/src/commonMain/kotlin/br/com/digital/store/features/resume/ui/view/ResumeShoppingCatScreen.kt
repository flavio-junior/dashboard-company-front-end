package br.com.digital.store.features.resume.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.components.ui.ResourceUnavailable

@Composable
fun ResumeShoppingCatScreen(
    pierChart: PierChart
) {
    if (pierChart.graphic != null) {
        AnaliseDay(pierChart = pierChart, enabled = false)
    } else {
        ResourceUnavailable()
    }
}
