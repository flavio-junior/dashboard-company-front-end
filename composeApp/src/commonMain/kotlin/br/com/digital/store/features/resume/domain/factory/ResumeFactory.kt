package br.com.digital.store.features.resume.domain.factory

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.DELIVERY
import br.com.digital.store.components.strings.StringsUtils.ORDERS
import br.com.digital.store.components.strings.StringsUtils.PAYMENTS
import br.com.digital.store.components.strings.StringsUtils.PICKUP
import br.com.digital.store.components.strings.StringsUtils.RESERVATION
import br.com.digital.store.components.strings.StringsUtils.TYPE_PAYMENT
import br.com.digital.store.components.strings.StringsUtils.VALUE_TOTAL
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.bar_chart_4_bars
import br.com.digital.store.composeapp.generated.resources.money
import br.com.digital.store.composeapp.generated.resources.order
import br.com.digital.store.features.order.domain.factory.detailsOrderFactory
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.utils.OrderUtils.SHOPPING_CART
import br.com.digital.store.features.payment.domain.factory.paymentTypeFactory
import br.com.digital.store.features.resume.data.vo.AnaliseDayVO
import br.com.digital.store.features.resume.ui.view.DetailsPieChart
import br.com.digital.store.features.resume.ui.view.Graphic
import br.com.digital.store.features.resume.ui.view.InformationPieChat
import br.com.digital.store.features.resume.ui.view.PierChart
import br.com.digital.store.features.resume.ui.view.ResumePieChart
import br.com.digital.store.features.resume.utils.ColorsResume
import br.com.digital.store.features.resume.utils.ResumeUtils.NUMBER_DISCOUNT
import br.com.digital.store.features.resume.utils.ResumeUtils.NUMBER_ORDERS
import br.com.digital.store.features.resume.utils.ResumeUtils.TYPE_ORDERS
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.NumbersUtils
import br.com.digital.store.utils.formatterMaskToMoney

@Composable
fun ResumeFactory(
    label: String? = null,
    analiseDayVO: AnaliseDayVO,
    onItemSelected: (Pair<PierChart, Int>) -> Unit = {}
): PierChart {
    if (label != null) {
        val paymentType = converterLabelResume(label = label)
        onItemSelected(GoToNextResumes(analiseDayVO = analiseDayVO, arguments = paymentType))
    }
    return PopulateOrdersDay(analiseDayVO = analiseDayVO)
}

private fun converterLabelResume(label: String): Pair<TypeOrder?, Int> {
    return when (label) {
        SHOPPING_CART -> Pair(first = TypeOrder.SHOPPING_CART, second = NumbersUtils.NUMBER_ONE)
        DELIVERY -> Pair(first = TypeOrder.DELIVERY, second = NumbersUtils.NUMBER_TWO)
        RESERVATION -> Pair(first = TypeOrder.RESERVATION, second = NumbersUtils.NUMBER_FOUR)
        PICKUP -> Pair(first = TypeOrder.ORDER, second = NumbersUtils.NUMBER_THREE)
        else -> Pair(first = null, second = NumbersUtils.NUMBER_ZERO)
    }
}

@Composable
private fun GoToNextResumes(
    analiseDayVO: AnaliseDayVO,
    arguments: Pair<TypeOrder?, Int>
): Pair<PierChart, Int> {
    val filteredContent =
        analiseDayVO.content?.filter {
            it.analise?.any { payment ->
                payment.typeOrder == arguments.first
            } == true
        }
    return Pair(
        first = PierChart(
            graphic = Graphic(
                graphic = PAYMENTS,
                details = "$TYPE_PAYMENT:",
                information = filteredContent?.let { filteredList ->
                    var analiseIndex = 0
                    filteredList.flatMap { value ->
                        value.analise?.map { analiseItem ->
                            val color = ColorsResume()[analiseIndex % ColorsResume().size]
                            analiseIndex++
                            InformationPieChat(
                                label = paymentTypeFactory(payment = analiseItem.typePayment),
                                value = analiseItem.numberItems,
                                color = color
                            )
                        } ?: emptyList()
                    }
                } ?: emptyList(),
                total = filteredContent?.sumOf { it.numberOrders } ?: 0L
            ),
            resume = ResumePieChart(
                details = listOf(
                    DetailsPieChart(
                        label = NUMBER_ORDERS,
                        icon = Res.drawable.order,
                        value = filteredContent?.sumOf { it.numberOrders }?.toString() ?: EMPTY_TEXT
                    ),
                    DetailsPieChart(
                        label = VALUE_TOTAL,
                        icon = Res.drawable.money,
                        value = formatterMaskToMoney(price = filteredContent?.sumOf { it.total }
                            ?: 0.0)
                    ),
                    DetailsPieChart(
                        label = NUMBER_DISCOUNT,
                        icon = Res.drawable.bar_chart_4_bars,
                        value = filteredContent?.sumOf { it.discount }?.toString() ?: EMPTY_TEXT
                    )
                )
            )
        ),
        second = arguments.second
    )
}

@Composable
private fun PopulateOrdersDay(
    analiseDayVO: AnaliseDayVO
): PierChart {
    return PierChart(
        graphic = Graphic(
            graphic = ORDERS,
            details = TYPE_ORDERS,
            information = analiseDayVO.content?.mapIndexed { index, value ->
                InformationPieChat(
                    label = detailsOrderFactory(type = value.typeOrder),
                    value = value.numberOrders,
                    color = ColorsResume()[index]
                )
            },
            total = analiseDayVO.numberOrders
        ),
        resume = ResumePieChart(
            details = listOf(
                DetailsPieChart(
                    label = NUMBER_ORDERS,
                    icon = Res.drawable.order,
                    value = analiseDayVO.numberOrders.toString()
                ),
                DetailsPieChart(
                    label = VALUE_TOTAL,
                    icon = Res.drawable.money,
                    value = formatterMaskToMoney(price = analiseDayVO.total)
                ),
                DetailsPieChart(
                    label = NUMBER_DISCOUNT,
                    icon = Res.drawable.bar_chart_4_bars,
                    value = analiseDayVO.discount.toString()
                )
            )
        )
    )
}
