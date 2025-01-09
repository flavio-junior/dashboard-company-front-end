package br.com.digital.store.features.resume.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import br.com.digital.store.components.strings.StringsUtils.VALUE_TOTAL
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.SubTitle
import br.com.digital.store.components.ui.Title
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.bar_chart_4_bars
import br.com.digital.store.composeapp.generated.resources.money
import br.com.digital.store.composeapp.generated.resources.order
import br.com.digital.store.features.order.domain.factory.detailsOrderFactory
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.resume.data.vo.AnaliseDayVO
import br.com.digital.store.features.resume.data.vo.TypePaymentVO
import br.com.digital.store.features.resume.utils.ResumeUtils.ANALISE_DAY
import br.com.digital.store.features.resume.utils.ResumeUtils.NUMBER_DISCOUNT
import br.com.digital.store.features.resume.utils.ResumeUtils.NUMBER_ORDERS
import br.com.digital.store.features.resume.utils.ResumeUtils.RESUME_COMPLETED
import br.com.digital.store.features.resume.utils.ResumeUtils.TYPE_ORDERS
import br.com.digital.store.theme.SpaceSize.spaceSize50
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.formatterMaskToMoney
import br.com.digital.store.utils.onBorder
import org.jetbrains.compose.resources.DrawableResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun Float.toRadians(): Float = this * (PI / 180).toFloat()


@Composable
fun PieChart(
    analise: AnaliseDayVO,
    radiusOuter: Dp = Themes.size.spaceSize200,
    chartBarWidth: Dp = Themes.size.spaceSize128,
) {
    val totalSum = analise.numberOrders
    val floatValue = mutableListOf<Float>()
    analise.content?.forEachIndexed { index, values ->
        floatValue.add(
            index = index,
            element = 360 * values.numberOrders.toFloat() / totalSum.toFloat()
        )
    }

    val colors = listOf(
        Blue,
        Themes.colors.primary,
        Themes.colors.error,
        Themes.colors.secondary,
        Themes.colors.secondary,
        Yellow
    )

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
            radiusOuter = radiusOuter,
            chartBarWidth = chartBarWidth,
            floatValue = floatValue,
            colors = colors
        )
        BodyAnalise(
            listPayments = analise.content,
            numberOrders = analise.numberOrders,
            total = analise.total,
            discount = analise.discount,
            colors = colors,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
fun PierChartAnalise(
    modifier: Modifier = Modifier,
    radiusOuter: Dp = Themes.size.spaceSize200,
    chartBarWidth: Dp = Themes.size.spaceSize128,
    floatValue: List<Float>,
    colors: List<Color>
) {
    val myText = "Pedidos"
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(myText))
    val textSize = textLayoutResult.size
    var lastValue = 0f
    Box(modifier = modifier.fillMaxHeight()) {
        Title(title = ANALISE_DAY, modifier = Modifier.align(alignment = Alignment.TopStart))
        Canvas(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(radiusOuter * 2f)
                .padding(all = Themes.size.spaceSize36)
        ) {
            var startAngle = 0f
            floatValue.forEachIndexed { index, value ->
                drawArc(
                    color = colors[index],
                    startAngle,
                    value,
                    useCenter = false,
                    style = Stroke(spaceSize50.toPx(), cap = StrokeCap.Butt)
                )

                val angle = startAngle + value / 2f

                // Calcula as coordenadas x e y da porcentagem
                val percentageX = center.x + (radiusOuter.toPx() / 2f) * cos(angle.toRadians())
                val percentageY = center.y + (radiusOuter.toPx() / 2f) * sin(angle.toRadians())

                // Cria o texto da porcentagem, dividindo o valor por 2
                val percentageText = "${(value / 360f * 100 / 2).toInt()}%"

                // Mede o tamanho do texto da porcentagem
                val percentageTextLayoutResult = textMeasurer.measure(text = AnnotatedString(percentageText))
                val percentageTextSize = percentageTextLayoutResult.size

                // Desenha a porcentagem
                drawText(
                    textMeasurer, percentageText,
                    topLeft = Offset(
                        percentageX - percentageTextSize.width / 2f,
                        percentageY - percentageTextSize.height / 2f
                    ),
                    style = TextStyle(color = Color.Black) // Define a cor do texto como branco
                )

                // Atualiza startAngle para a próxima fatia
                startAngle += value
            }
        }
    }
}

@Composable
fun BodyAnalise(
    listPayments: List<TypePaymentVO>? = null,
    numberOrders: Long,
    total: Double,
    discount: Long,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Title(title = TYPE_ORDERS)
        ListPayments(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            listPayments = listPayments,
            colors = colors
        )
        Title(title = RESUME_COMPLETED)
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize8))
        DetailsCompletedAnalise(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            orders = numberOrders,
            total = total,
            discount = discount
        )
    }
}

@Composable
fun ListPayments(
    modifier: Modifier = Modifier,
    listPayments: List<TypePaymentVO>? = null,
    colors: List<Color>
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.Center
    ) {
        listPayments?.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(value.typeOrder, value.numberOrders.toInt()),
                color = colors[index]
            )
            Spacer(modifier = Modifier.height(height = Themes.size.spaceSize8))
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<TypeOrder, Int>,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .onBorder(
                    spaceSize = Themes.size.spaceSize12,
                    width = Themes.size.spaceSize2,
                    color = color
                )
                .background(color = color)
                .size(size = Themes.size.spaceSize48)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
        ) {
            SubTitle(subTitle = detailsOrderFactory(type = data.first))
            Description(description = data.second.toString())
        }
    }
}

@Composable
fun DetailsCompletedAnalise(
    modifier: Modifier = Modifier,
    orders: Long,
    total: Double,
    discount: Long,
) {
    Column(
        modifier = modifier.padding(all = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        LabelWithInfoAnalise(
            icon = Res.drawable.order,
            label = NUMBER_ORDERS,
            description = orders.toString()
        )
        LabelWithInfoAnalise(
            icon = Res.drawable.money,
            label = VALUE_TOTAL,
            description = formatterMaskToMoney(price = total)
        )
        LabelWithInfoAnalise(
            icon = Res.drawable.bar_chart_4_bars,
            label = NUMBER_DISCOUNT,
            description = discount.toString()
        )
    }
}

@Composable
fun LabelWithInfoAnalise(
    label: String,
    description: String,
    icon: DrawableResource
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            icon = icon,
            size = Themes.size.spaceSize48
        )
        Spacer(modifier = Modifier.width(width = Themes.size.spaceSize8))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        ) {
            SubTitle(subTitle = label)
            Description(description = description)
        }
    }
}
