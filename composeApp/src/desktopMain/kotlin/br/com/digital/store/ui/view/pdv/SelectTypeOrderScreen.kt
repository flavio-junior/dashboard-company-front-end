package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.Title
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.arrow_outward
import br.com.digital.store.features.pdv.utils.TypeNavigation
import br.com.digital.store.features.pdv.utils.TypeOrder
import br.com.digital.store.features.pdv.utils.selectTypeOrder
import br.com.digital.store.navigation.RouteApp
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder

@Composable
fun SelectTypeOrderScreen(
    goToNextTab: (Int) -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(color = Themes.colors.background).fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            selectTypeOrder.forEach {
                SelectTypeOrder(
                    type = it,
                    goToNextTab = goToNextTab,
                    goToNextScreen = goToNextScreen
                )
            }
        }
    }
}

@Composable
fun SelectTypeOrder(
    type: TypeOrder,
    goToNextTab: (Int) -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .onBorder(
                onClick = {
                    if (type.navigation == TypeNavigation.NAVIGATION) {
                        goToNextScreen(RouteApp.Order.item)
                    } else {
                        goToNextTab(type.count)
                    }
                },
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .padding(all = Themes.size.spaceSize16)
            .width(width = Themes.size.spaceSize650)
    ) {
        IconDefault(icon = type.icon, size = Themes.size.spaceSize48)
        Title(title = type.label.uppercase(), modifier = Modifier.weight(weight = WEIGHT_SIZE))
        IconDefault(icon = Res.drawable.arrow_outward, size = Themes.size.spaceSize48)
    }
}
