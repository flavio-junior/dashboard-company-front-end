package br.com.digital.store.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.Title
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.model.ItemService
import br.com.digital.store.strings.StringsUtils.DASHBOARD
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.changeColor
import br.com.digital.store.utils.onBorder
import lojavirtual.composeapp.generated.resources.Res
import lojavirtual.composeapp.generated.resources.box

@Composable
fun Services(
    label: String,
    services: List<ItemService> = availableServices,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = Modifier
            .padding(all = Themes.size.spaceSize16)
    ) {
        services.forEach { item ->
            if (item.label == label) Service(
                enabled = true,
                service = item,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            ) else Service(
                service = item,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    }
}

@Composable
fun Service(
    enabled: Boolean = false,
    service: ItemService,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
            .onBorder(
                onClick = {
                    if (service.route == DASHBOARD) goToBackScreen() else goToNextScreen(service.route)
                },
                width = Themes.size.spaceSize2,
                spaceSize = Themes.size.spaceSize8,
                color = Themes.colors.primary
            )
            .changeColor(enabled = enabled)
            .width(width = Themes.size.spaceSize300)
            .padding(all = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(icon = Res.drawable.box)
        Title(title = service.label)
    }
}
