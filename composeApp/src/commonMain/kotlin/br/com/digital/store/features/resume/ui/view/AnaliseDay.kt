package br.com.digital.store.features.resume.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE

@Composable
fun AnaliseDay(
    pierChart: PierChart
) {
    var animationPlayed by remember { mutableStateOf(value = false) }
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(
            top = Themes.size.spaceSize16,
            end = Themes.size.spaceSize16
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PierChartAnalise(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            graphic = pierChart.graphic
        )
        DetailsAnalise(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            graphic = pierChart.graphic,
            resume = pierChart.resume
        )
    }
}
