package br.com.digital.store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.model.Menu
import br.com.digital.store.components.ui.ItemMenu
import br.com.digital.store.domain.factory.menus
import br.com.digital.store.navigation.ItemNavigation
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils.NUMBER_FOUR
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlin.system.exitProcess

@Composable
fun DashboardScreen(
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        body = {
            MainCard(
                menus = menus,
                goToNextScreen = goToNextScreen
            )
        }
    )
}

@Composable
private fun MainCard(
    modifier: Modifier = Modifier,
    menus: List<Menu>,
    goToNextScreen: (String) -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxHeight()
            .padding(all = Themes.size.spaceSize36)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = NUMBER_FOUR),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            items(menus) { menu ->
                ItemMenu(menu = menu, goToNextScreen = {
                    if (it == ItemNavigation.EXIT.name) {
                        exitProcess(status = NUMBER_ZERO)
                    } else {
                        goToNextScreen(it)
                    }
                })
            }
        }
    }
}
