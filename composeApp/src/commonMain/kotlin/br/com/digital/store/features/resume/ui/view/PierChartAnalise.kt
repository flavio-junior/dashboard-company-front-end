package br.com.digital.store.features.resume.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import br.com.digital.store.components.strings.StringsUtils.TYPE_ANALYSIS
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.resume.domain.factory.analiseDayFactory
import br.com.digital.store.features.resume.domain.type.TypeAnalysis
import br.com.digital.store.features.resume.ui.viewmodel.ResumeViewModel
import br.com.digital.store.features.resume.utils.listAnalise
import br.com.digital.store.features.resume.utils.toRadians
import br.com.digital.store.theme.FontSize.fontSize18
import br.com.digital.store.theme.FontSize.fontSize36
import br.com.digital.store.theme.SpaceSize.spaceSize48
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PierChartAnalise(
    modifier: Modifier,
    label: String,
    enabled: Boolean = true,
    radiusOuter: Dp = Themes.size.spaceSize250,
    graphic: Graphic? = null,
    refreshPage: (Pair<TypeAnalysis, String>) -> Unit = {}
) {
    val viewModel: ResumeViewModel = getKoin().get()
    val titleGraphic = graphic?.graphic
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text = titleGraphic ?: EMPTY_TEXT),
        style = TextStyle(
            color = Color.Black,
            fontSize = fontSize36,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    )
    val textSize = textLayoutResult.size
    Box(modifier = modifier.fillMaxHeight()) {
        if (enabled) {
            var itemSelected: String by remember { mutableStateOf(value = label) }
            DropdownMenu(
                selectedValue = itemSelected,
                items = listAnalise,
                label = TYPE_ANALYSIS,
                onValueChangedEvent = {
                    itemSelected = it
                    val converterAnalise = analiseDayFactory(label = it)
                    refreshPage(Pair(first = converterAnalise, second = it))
                },
                modifier = Modifier.align(alignment = Alignment.TopStart)
            )
        } else {
            Title(title = viewModel.analiseDay)
        }
        Canvas(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(size = radiusOuter * 2f)
                .padding(all = Themes.size.spaceSize36)
        ) {
            val total = graphic?.total ?: 0
            var startAngle = 0f
            val spacingAngle = 5f
            graphic?.information?.forEach { analytic ->
                val sweepAngle = (analytic.value.toFloat() / total) * 360f
                drawArc(
                    color = analytic.color,
                    startAngle = startAngle + spacingAngle / 2f,
                    sweepAngle = sweepAngle - spacingAngle,
                    useCenter = false,
                    style = Stroke(spaceSize48.toPx(), cap = StrokeCap.Butt)
                )
                val percentageX: Float
                val percentageY: Float
                val percentage = "${((analytic.value.toFloat() / total) * 100).toInt()}%"
                val percentageTextLayoutResult =
                    textMeasurer.measure(text = AnnotatedString(percentage))
                val percentageTextSize = percentageTextLayoutResult.size
                if (total == 1L) {
                    percentageX = (this.size.width - percentageTextSize.width) / 2f
                    percentageY = this.size.height - percentageTextSize.height - spaceSize48.toPx()
                } else {
                    val angle = startAngle + sweepAngle / 2f
                    percentageX = center.x + (radiusOuter.toPx() / 2f) * cos(angle.toRadians())
                    percentageY = center.y + (radiusOuter.toPx() / 2f) * sin(angle.toRadians())
                }
                drawText(
                    textMeasurer = textMeasurer,
                    text = percentage,
                    topLeft = Offset(
                        x = percentageX,
                        y = percentageY
                    ),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = fontSize18,
                        fontWeight = FontWeight.Bold
                    )
                )
                startAngle += sweepAngle
            }
            drawText(
                textMeasurer = textMeasurer,
                text = titleGraphic ?: EMPTY_TEXT,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = fontSize36,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                topLeft = Offset(
                    x = (this.size.width - textSize.width) / 2f,
                    y = (this.size.height - textSize.height) / 2f
                )
            )
        }
    }
}
