package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.AlternativeButton
import br.com.digital.store.features.pdv.utils.selectBodyButtons
import br.com.digital.store.navigation.RouteApp
import br.com.digital.store.theme.Themes

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
            selectBodyButtons.forEach {
                AlternativeButton(
                    type = it,
                    goToNextTab = goToNextTab,
                    goToNextScreen = {
                        goToNextScreen(RouteApp.Order.item)
                    }
                )
            }
        }
    }
}
