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
import br.com.digital.store.components.strings.StringsUtils.ANALISE_DAY
import br.com.digital.store.components.ui.EmptyList
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.data_usage
import br.com.digital.store.features.resume.domain.type.TypeAnalysis
import br.com.digital.store.features.resume.utils.ResumeUtils.EMPTY_LIST_RESUME
import br.com.digital.store.features.resume.utils.ResumeUtils.NONE_RESUME
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE

@Composable
fun AnaliseDay(
    pierChart: PierChart,
    label: String = ANALISE_DAY,
    enabled: Boolean = true,
    onItemSelect: (Pair<Boolean, String>) -> Unit = {},
    goToOrderScreen: () -> Unit = {},
    refreshPage: (Pair<TypeAnalysis, String>) -> Unit = {}
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
        if (pierChart.graphic?.total != 0L && pierChart.graphic?.information?.isNotEmpty() == true) {
            PierChartAnalise(
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
                label = label,
                enabled = enabled,
                graphic = pierChart.graphic,
                refreshPage = refreshPage
            )
            DetailsAnalise(
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
                graphic = pierChart.graphic,
                resume = pierChart.resume,
                onItemSelect = onItemSelect
            )
        } else {
            EmptyList(
                title = EMPTY_LIST_RESUME,
                description = NONE_RESUME,
                mainIcon = Res.drawable.data_usage,
                onClick = goToOrderScreen,
                refresh = {
                    refreshPage(Pair(first = TypeAnalysis.DAY, second = ANALISE_DAY))
                }
            )
        }
    }
}
