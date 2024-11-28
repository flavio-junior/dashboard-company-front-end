package br.com.digital.store.ui.view.item

import androidx.compose.runtime.Composable
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.strings.StringsUtils.ITEMS
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun ItemScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = ITEMS,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            CardItems()
        }
    )
}
