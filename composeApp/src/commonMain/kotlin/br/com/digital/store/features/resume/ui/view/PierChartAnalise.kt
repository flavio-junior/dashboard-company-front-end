package br.com.digital.store.features.resume.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.resume.utils.toRadians
import br.com.digital.store.theme.FontSize.fontSize18
import br.com.digital.store.theme.FontSize.fontSize36
import br.com.digital.store.theme.SpaceSize.spaceSize48
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PierChartAnalise(
    modifier: Modifier,
    radiusOuter: Dp = Themes.size.spaceSize200,
    graphic: Graphic? = null,
) {
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
        Title(
            title = graphic?.title ?: EMPTY_TEXT,
            modifier = Modifier.align(alignment = Alignment.TopStart)
        )
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
                val angle = startAngle + sweepAngle / 2f
                val percentageX = center.x + (radiusOuter.toPx() / 2f) * cos(angle.toRadians())
                val percentageY = center.y + (radiusOuter.toPx() / 2f) * sin(angle.toRadians())
                val percentage = "${((analytic.value.toFloat() / total) * 100).toInt()}%"
                val percentageTextLayoutResult =
                    textMeasurer.measure(text = AnnotatedString(percentage))
                val percentageTextSize = percentageTextLayoutResult.size
                drawText(
                    textMeasurer = textMeasurer,
                    text = percentage,
                    topLeft = Offset(
                        x = percentageX - percentageTextSize.width / 2f,
                        y = percentageY - percentageTextSize.height / 2f
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
