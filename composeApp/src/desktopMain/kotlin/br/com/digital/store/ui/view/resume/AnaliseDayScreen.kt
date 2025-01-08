package br.com.digital.store.ui.view.resume

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.resume.data.vo.AnalisePaymentResponseVO
import br.com.digital.store.features.resume.ui.viewmodel.ResumeViewModel
import br.com.digital.store.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun GetAnaliseDayScreen() {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.success)
            .fillMaxSize()
    ) {
        val viewModel: ResumeViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.getAnalysisDay()
        }
        ObserveNetworkStateHandlerGetAnaliseDay(
            viewModel = viewModel
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerGetAnaliseDay(
    viewModel: ResumeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
) {
    val state: ObserveNetworkStateHandler<AnalisePaymentResponseVO> by remember { viewModel.getAnalysisDay }
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
            Description(description = it.result?.analise.toString())
        }
    )
}
