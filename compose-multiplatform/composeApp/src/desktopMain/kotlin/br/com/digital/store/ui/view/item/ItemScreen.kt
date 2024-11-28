package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.features.item.data.vo.ItemResponseVO
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.components.strings.StringsUtils.ITEMS
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.TypeLayout

@Composable
fun ItemScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            var item: ItemResponseVO by remember { mutableStateOf(value = ItemResponseVO()) }
            Row {
                Services(
                    label = ITEMS,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                CardItems(
                    modifier = Modifier
                        .weight(weight = WEIGHT_SIZE_2),
                    onItemSelected = { item = it }
                )
                EditItem(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
                        .weight(weight = WEIGHT_SIZE),
                    itemVO = item,
                    onCleanItem = {
                        item = ItemResponseVO()
                    }
                )
            }
        }
    )
}
