package br.com.digital.store.ui.view.employee

import androidx.compose.runtime.Composable
import br.com.digital.store.components.strings.StringsUtils.EMPLOYEES
import br.com.digital.store.domain.factory.settings
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun EmployeeScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = EMPLOYEES,
                services = settings,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    )
}
