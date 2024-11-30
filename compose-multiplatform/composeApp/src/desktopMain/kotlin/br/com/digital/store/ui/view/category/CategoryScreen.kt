package br.com.digital.store.ui.view.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CATEGORIES
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.features.category.data.vo.CategoryResponseVO
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.TypeLayout

@Composable
fun CategoryScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var categoryVO: CategoryResponseVO by remember { mutableStateOf(value = CategoryResponseVO()) }
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Row {
                Services(
                    label = CATEGORIES,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                CardCategories(
                    modifier = Modifier
                        .weight(weight = WEIGHT_SIZE_2),
                    onItemSelected = { categoryVO = it },
                    goToAlternativeRoutes = goToAlternativeRoutes
                )
                EditCategory(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
                        .weight(weight = WEIGHT_SIZE),
                    categoryVO = categoryVO,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onCleanCategory = {
                        categoryVO = CategoryResponseVO()
                    }
                )
            }
        }
    )
}
