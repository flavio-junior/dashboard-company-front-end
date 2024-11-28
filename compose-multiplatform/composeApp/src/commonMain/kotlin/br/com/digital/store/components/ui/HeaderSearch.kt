package br.com.digital.store.components.ui

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
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.refresh
import br.com.digital.store.components.strings.StringsUtils.ASC
import br.com.digital.store.components.strings.StringsUtils.SIZE_LIST
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.SIZE_DEFAULT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.LocationRoute
import br.com.digital.store.utils.converterSizeStringToInt
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.sizeList

@Composable
fun HeaderSearch(
    modifier: Modifier = Modifier,
    onSearch: (String, Int, String, LocationRoute) -> Unit,
    onSort: (String, Int, String, LocationRoute) -> Unit,
    onFilter: (String, Int, String, LocationRoute) -> Unit,
    onRefresh: (String, Int, String, LocationRoute) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        var sortBy: String by remember { mutableStateOf(value = ASC) }
        var size: String by remember { mutableStateOf(value = SIZE_DEFAULT) }
        Search(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            onGo = {
                onSearch(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.SEARCH)
            }
        )
        SortBy(
            onClick = {
                sortBy = it
                onSort(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.SORT)
            }
        )
        DropdownMenu(
            selectedValue = size,
            items = sizeList,
            label = SIZE_LIST,
            onValueChangedEvent = {
                size = it
                onFilter(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.FILTER)
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
                onRefresh(name, converterSizeStringToInt(size = size), sortBy, LocationRoute.RELOAD)
            }
        )
    }
}