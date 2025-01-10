package br.com.digital.store.ui.view.resume

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ORDERS
import br.com.digital.store.components.strings.StringsUtils.VALUE_TOTAL
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.bar_chart_4_bars
import br.com.digital.store.composeapp.generated.resources.money
import br.com.digital.store.composeapp.generated.resources.order
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.domain.factory.detailsOrderFactory
import br.com.digital.store.features.resume.data.vo.AnaliseDayVO
import br.com.digital.store.features.resume.ui.view.AnaliseDay
import br.com.digital.store.features.resume.ui.view.DetailsPieChart
import br.com.digital.store.features.resume.ui.view.Graphic
import br.com.digital.store.features.resume.ui.view.InformationPieChat
import br.com.digital.store.features.resume.ui.view.PierChart
import br.com.digital.store.features.resume.ui.view.ResumePieChart
import br.com.digital.store.features.resume.ui.viewmodel.ResumeViewModel
import br.com.digital.store.features.resume.utils.ColorsResume
import br.com.digital.store.features.resume.utils.ResumeUtils.ANALISE_DAY
import br.com.digital.store.features.resume.utils.ResumeUtils.NUMBER_DISCOUNT
import br.com.digital.store.features.resume.utils.ResumeUtils.NUMBER_ORDERS
import br.com.digital.store.features.resume.utils.ResumeUtils.RESUME_COMPLETED
import br.com.digital.store.features.resume.utils.ResumeUtils.TYPE_ORDERS
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.formatterMaskToMoney
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun GetAnaliseDayScreen() {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
    ) {
        val viewModel: ResumeViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.getAnalysisDay()
        }
        ObserveNetworkStateHandlerGetAnaliseDay(
            viewModel = viewModel,
            onSuccess = {

                val pieChart = PierChart(
                    graphic = Graphic(
                        title = ANALISE_DAY,
                        graphic = ORDERS,
                        details = TYPE_ORDERS,
                        information = it.content?.mapIndexed { index, value ->
                            InformationPieChat(
                                label = detailsOrderFactory(type = value.typeOrder),
                                value = value.numberOrders,
                                color = ColorsResume()[index]
                            )
                        },
                        total = it.numberOrders
                    ),
                    resume = ResumePieChart(
                        title = RESUME_COMPLETED,
                        details = listOf(
                            DetailsPieChart(
                                label = NUMBER_ORDERS,
                                icon = Res.drawable.order,
                                value = it.numberOrders.toString()
                            ),
                            DetailsPieChart(
                                label = VALUE_TOTAL,
                                icon = Res.drawable.money,
                                value = formatterMaskToMoney(price = it.total)
                            ),
                            DetailsPieChart(
                                label = NUMBER_DISCOUNT,
                                icon = Res.drawable.bar_chart_4_bars,
                                value = it.discount.toString()
                            )
                        )
                    )
                )
                AnaliseDay(pierChart = pieChart)
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerGetAnaliseDay(
    viewModel: ResumeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (AnaliseDayVO) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<AnaliseDayVO> by remember { viewModel.getAnalysisDay }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Triple(first = true, second = false, third = it)
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            it.result?.let { result -> onSuccess(result) }
        }
    )
}
