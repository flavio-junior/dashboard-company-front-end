package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.DELIVERY
import br.com.digital.store.components.strings.StringsUtils.TYPE_ORDER
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.typeOrder

@Composable
fun CashRegisterScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .onBorder(
                onClick = { },
                width = Themes.size.spaceSize2,
                spaceSize = Themes.size.spaceSize8,
                color = Themes.colors.primary
            )
            .background(color = Themes.colors.background)
            .padding(all = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var itemSelected: String by remember { mutableStateOf(value = DELIVERY) }
        DropdownMenu(
            selectedValue = itemSelected,
            items = typeOrder,
            label = TYPE_ORDER,
            onValueChangedEvent = {
                itemSelected = it
            }
        )
        Row {
            CreateOrder(itemSelected = itemSelected)
        }
    }
}

@Composable
private fun CreateOrder(
    modifier: Modifier = Modifier,
    itemSelected: String = DELIVERY
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxHeight()
    ) {
        CashRegisterFields(itemSelected = itemSelected)
    }
}
