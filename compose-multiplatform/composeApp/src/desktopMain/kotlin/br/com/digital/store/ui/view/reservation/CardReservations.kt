package br.com.digital.store.ui.view.reservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.common.reservation.vo.ReservationResponseVO
import br.com.digital.store.common.reservation.vo.ReservationsResponseVO
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingData
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.reservation.viewmodel.ReservationViewModel
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_5
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CardReservations() {
    Column(
        modifier = Modifier
            .padding(all = Themes.size.spaceSize16)
    ) {
        HeaderSearchReservations()
        val viewModel: ReservationViewModel = getKoin().get()
        LaunchedEffect(key1 = Unit) {
            viewModel.findAllReservations()
        }
        ObserveNetworkStateHandlerReservations(viewModel = viewModel)
    }
}

@Composable
private fun ObserveNetworkStateHandlerReservations(
    viewModel: ReservationViewModel
) {
    val state: ObserveNetworkStateHandler<ReservationsResponseVO> by remember { viewModel.findAllReservations }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {
            LoadingData()
        },
        onError = {
            Description(description = it)
        },
        onSuccess = {
            it.result?.let { response ->
                AllServicesReservations(content = response)
            }
        }
    )
}

@Composable
private fun AllServicesReservations(
    content: ReservationsResponseVO
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize().weight(weight = WEIGHT_SIZE_5)
                .weight(weight = WEIGHT_SIZE_4)
        ) {
            var reservation: ReservationResponseVO by remember { mutableStateOf(value = ReservationResponseVO()) }
            ListReservations(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = WEIGHT_SIZE_4)
                    .padding(top = Themes.size.spaceSize24),
                content = content,
                onItemSelected = { reservation = it }
            )
            EditReservation(
                reservationVO = reservation,
                modifier = Modifier
                    .padding(start = Themes.size.spaceSize16, top = Themes.size.spaceSize36)
                    .weight(weight = WEIGHT_SIZE_2),
                onCleanReservations = {
                    reservation = ReservationResponseVO()
                },
                onSuccessful = {
                    reservation = it
                }
            )
        }
        PageIndicatorReservations(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            content = content
        )
        Row(
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        ) {
            SaveReservation(modifier = Modifier.weight(weight = WEIGHT_SIZE_4))
            Spacer(modifier = Modifier.weight(weight = WEIGHT_SIZE_2))
        }
    }
}
