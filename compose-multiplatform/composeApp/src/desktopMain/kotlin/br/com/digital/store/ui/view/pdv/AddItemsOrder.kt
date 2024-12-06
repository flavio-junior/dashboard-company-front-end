package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_FOOD
import br.com.digital.store.components.strings.StringsUtils.ADD_ITEM
import br.com.digital.store.components.strings.StringsUtils.ADD_PRODUCT
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE

@Composable
fun AddItemsOrder() {
    Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
        LoadingButton(
            label = ADD_PRODUCT,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            label = ADD_FOOD,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            label = ADD_ITEM,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}
