package br.com.digital.store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.model.Menu
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ItemMenu
import br.com.digital.store.domain.factory.menus
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.NumbersUtils.NUMBER_FOUR

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Row(modifier = Modifier.fillMaxSize()) {
        MainCard(modifier = modifier.weight(weight = WEIGHT_SIZE_4), menus = menus)
        DetailsCard(modifier = modifier.weight(weight = WEIGHT_SIZE))
    }
}

@Composable
private fun MainCard(
    modifier: Modifier = Modifier,
    menus: List<Menu>
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
                ItemMenu(
                    icon = menu.icon,
                    label = menu.label
                )
            }
        }
    }
}

@Composable
private fun DetailsCard(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.success)
            .fillMaxHeight()
    ) {
        Description(description = "Data")
        Description(description = "Name")
        Description(description = "Email")
    }
}
