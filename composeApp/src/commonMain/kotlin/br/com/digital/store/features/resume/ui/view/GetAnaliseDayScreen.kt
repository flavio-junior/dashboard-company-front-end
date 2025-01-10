package br.com.digital.store.features.resume.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.resume.data.vo.AnaliseDayVO
import br.com.digital.store.features.resume.domain.factory.ResumeFactory
import br.com.digital.store.features.resume.ui.viewmodel.ResumeViewModel
import br.com.digital.store.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun GetAnaliseDayScreen(
    onItemSelected: (Pair<PierChart, Int>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Pair<Boolean, String?> by remember {
        mutableStateOf(value = Pair(first = false, second = null))
    }
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
                AnaliseDay(
                    pierChart = ResumeFactory(
                        label = observer.second,
                        analiseDayVO = it,
                        onItemSelected = onItemSelected
                    ),
                    onItemSelect = { itemSelected ->
                        observer = itemSelected
                    }
                )
            },
            goToAlternativeRoutes = goToAlternativeRoutes
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
