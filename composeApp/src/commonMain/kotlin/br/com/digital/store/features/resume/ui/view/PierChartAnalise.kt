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
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import br.com.digital.store.components.strings.StringsUtils.ORDERS
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.resume.utils.ResumeUtils.ANALISE_DAY
import br.com.digital.store.features.resume.utils.toRadians
import br.com.digital.store.theme.SpaceSize.spaceSize48
import br.com.digital.store.theme.Themes
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PierChartAnalise(
    modifier: Modifier,
    radiusOuter: Dp = Themes.size.spaceSize200,
    floatValue: List<Float>,
    colors: List<Color>
) {
    val myText = ORDERS
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(myText))
    val textSize = textLayoutResult.size
    Box(modifier = modifier.fillMaxHeight()) {
        Title(title = ANALISE_DAY, modifier = Modifier.align(alignment = Alignment.TopStart))
        Canvas(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(size = radiusOuter * 2f)
                .padding(all = Themes.size.spaceSize36)
        ) {
            val total = floatValue.sum()
            var startAngle = 0f
            floatValue.forEachIndexed { index, value ->
                drawArc(
                    color = colors[index],
                    startAngle,
                    value,
                    useCenter = false,
                    style = Stroke(spaceSize48.toPx(), cap = StrokeCap.Butt)
                )
                val angle = startAngle + value / 2f
                val percentageX = center.x + (radiusOuter.toPx() / 2f) * cos(angle.toRadians())
                val percentageY = center.y + (radiusOuter.toPx() / 2f) * sin(angle.toRadians())
                val percentageText = "${((value / total) * 100).toInt()}%"
                val percentageTextLayoutResult =
                    textMeasurer.measure(text = AnnotatedString(percentageText))
                val percentageTextSize = percentageTextLayoutResult.size
                drawText(
                    textMeasurer, percentageText,
                    topLeft = Offset(
                        x = percentageX - percentageTextSize.width / 2f,
                        y = percentageY - percentageTextSize.height / 2f
                    ),
                    style = TextStyle(color = Color.Black)
                )
                startAngle += value
            }
            drawText(
                textMeasurer, myText,
                topLeft = Offset(
                    x = (this.size.width - textSize.width) / 2f,
                    y = (this.size.height - textSize.height) / 2f
                ),
            )
        }
    }
}
