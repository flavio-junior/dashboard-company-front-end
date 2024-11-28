package br.com.digital.store.ui.view.reservation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.common.reservation.vo.ReservationResponseVO
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.strings.StringsUtils.RESERVATION
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.TypeLayout

@Composable
fun ReservationScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            var responseVO: ReservationResponseVO by remember { mutableStateOf(value = ReservationResponseVO()) }
            Row {
                Services(
                    label = RESERVATION,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                CardReservations(
                    modifier = Modifier
                        .weight(weight = WEIGHT_SIZE_2),
                    onItemSelected = { responseVO = it }
                )
                EditReservation(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
                        .weight(weight = WEIGHT_SIZE),
                    reservationVO = responseVO,
                    onCleanReservations = {
                        responseVO = ReservationResponseVO()
                    }
                )
            }
        }
    )
}
