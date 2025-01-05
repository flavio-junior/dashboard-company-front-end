package br.com.digital.store.ui.view.payment

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.PAYMENTS
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.navigation.RouteApp
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun PaymentScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = PAYMENTS,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            ListPaymentsScreen(
                goToOrderScreen = {
                    goToNextScreen(RouteApp.Pdv.item)
                }
            )
        }
    )
}
