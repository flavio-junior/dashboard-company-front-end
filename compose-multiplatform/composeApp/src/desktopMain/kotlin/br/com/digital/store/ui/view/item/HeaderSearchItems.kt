package br.com.digital.store.ui.view.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.Search
import br.com.digital.store.components.ui.SortBy
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.refresh
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import br.com.digital.store.strings.StringsUtils.ASC
import br.com.digital.store.strings.StringsUtils.SIZE_LIST
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.SIZE_DEFAULT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.converterSizeStringToInt
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.sizeList
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun HeaderSearchItems(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val viewModel: ItemViewModel = getKoin().get()
        var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        var sortBy: String by remember { mutableStateOf(value = ASC) }
        var size: String by remember { mutableStateOf(value = SIZE_DEFAULT) }
        Search(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            onGo = {
                viewModel.findAllItems(
                    name = name,
                    size = converterSizeStringToInt(size = size),
                    sort = sortBy,
                    route = LocationRoute.SEARCH
                )
            }
        )
        SortBy(
            onClick = {
                sortBy = it
                viewModel.findAllItems(
                    name = name,
                    size = converterSizeStringToInt(size = size),
                    sort = sortBy,
                    route = LocationRoute.SORT
                )
            }
        )
        DropdownMenu(
            selectedValue = size,
            items = sizeList,
            label = SIZE_LIST,
            onValueChangedEvent = {
                size = it
                viewModel.findAllItems(
                    name = name,
                    size = converterSizeStringToInt(size = size),
                    sort = sortBy,
                    route = LocationRoute.FILTER
                )
            }
        )
        IconDefault(
            icon = Res.drawable.refresh, modifier = Modifier
                .onBorder(
                    onClick = {},
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize64)
                .padding(all = Themes.size.spaceSize8),
            onClick = {
                viewModel.findAllItems(
                    name = name,
                    size = converterSizeStringToInt(size = size),
                    sort = sortBy,
                    route = LocationRoute.RELOAD
                )
            }
        )
    }
}
