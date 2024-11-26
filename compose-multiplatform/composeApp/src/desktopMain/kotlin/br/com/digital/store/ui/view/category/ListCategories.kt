package br.com.digital.store.ui.view.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.Search
import br.com.digital.store.components.ui.SortBy
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.refresh
import br.com.digital.store.strings.StringsUtils.ASC
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder

@Composable
fun ListCategories(
    modifier: Modifier = Modifier,
    categories: List<CategoryResponseVO>,
    onItemSelected: (CategoryResponseVO) -> Unit = {},
    findAllCategories: (Pair<String, String>) -> Unit = {}
) {
    Column(
        modifier = modifier.background(color = Themes.colors.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var search: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var sortBy: String by remember { mutableStateOf(value = ASC) }
            Search(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
                onGo = {
                    findAllCategories(Pair(search, sortBy))
                }
            )
            SortBy(onClick = { sortBy = it })
            IconDefault(
                icon = Res.drawable.refresh, modifier = Modifier
                    .onBorder(
                        onClick = {},
                        color = Themes.colors.primary,
                        spaceSize = Themes.size.spaceSize16,
                        width = Themes.size.spaceSize1
                    )
                    .size(size = Themes.size.spaceSize64)
                    .padding(all = Themes.size.spaceSize8),
                onClick = {
                    findAllCategories(Pair(search, sortBy))
                }
            )
        }
        CategoriesPanel(
            modifier = modifier.padding(top = Themes.size.spaceSize16),
            categories = categories,
            onItemSelected = onItemSelected
        )
    }
}
