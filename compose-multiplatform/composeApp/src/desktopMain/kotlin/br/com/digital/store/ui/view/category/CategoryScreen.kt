package br.com.digital.store.ui.view.category

import androidx.compose.runtime.Composable
import br.com.digital.store.components.ui.Description
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.strings.StringsUtils.CATEGORIES
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.TypeLayout

@Composable
fun CategoryScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Services(
                label = CATEGORIES,
                services = availableServices,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
            CardCategories()
        }
    )
}
