package br.com.digital.store.ui.view.reservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.HeaderSearch
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.reservation.data.vo.ReservationResponseVO
import br.com.digital.store.features.reservation.data.vo.ReservationsResponseVO
import br.com.digital.store.features.reservation.ui.viewmodel.ReservationViewModel
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CardReservations(
    modifier: Modifier = Modifier,
    onItemSelected: (ReservationResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            )
    ) {
        val viewModel: ReservationViewModel = getKoin().get()
        HeaderSearch(
            onSearch = { name, size, sort, route ->
                viewModel.findAllReservations(name = name, size = size, sort = sort, route = route)
            },
            onSort = { name, size, sort, route ->
                viewModel.findAllReservations(name = name, size = size, sort = sort, route = route)
            },
            onFilter = { name, size, sort, route ->
                viewModel.findAllReservations(name = name, size = size, sort = sort, route = route)
            },
            onRefresh = { name, size, sort, route ->
                viewModel.findAllReservations(name = name, size = size, sort = sort, route = route)
            }
        )
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllReservations()
        }
        ObserveNetworkStateHandlerReservations(
            viewModel = viewModel,
            onItemSelected = onItemSelected,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerReservations(
    viewModel: ReservationViewModel,
    onItemSelected: (ReservationResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<ReservationsResponseVO> by remember { viewModel.findAllReservations }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {
            LoadingData()
        },
        onError = {
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            it.result?.let { response ->
                ReservationsResult(
                    content = response,
                    onItemSelected = onItemSelected,
                    goToAlternativeRoutes = goToAlternativeRoutes
                )
            }
        }
    )
}

@Composable
private fun ReservationsResult(
    content: ReservationsResponseVO,
    onItemSelected: (ReservationResponseVO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column {
        ListReservations(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = WEIGHT_SIZE_4)
                .padding(top = Themes.size.spaceSize8),
            content = content,
            onItemSelected = onItemSelected
        )
        PageIndicatorReservations(content = content)
        SaveReservation(goToAlternativeRoutes = goToAlternativeRoutes)
    }
}
