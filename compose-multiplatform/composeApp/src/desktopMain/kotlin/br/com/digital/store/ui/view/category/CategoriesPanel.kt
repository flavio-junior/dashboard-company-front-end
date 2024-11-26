package br.com.digital.store.ui.view.category

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.components.ui.Description
import br.com.digital.store.strings.StringsUtils.ID
import br.com.digital.store.strings.StringsUtils.NAME
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.onClickable
import kotlinx.coroutines.launch

@Composable
fun CategoriesPanel(
    modifier: Modifier = Modifier,
    categories: List<CategoryResponseVO>,
    onItemSelected: (CategoryResponseVO) -> Unit
) {
    HeaderCategoriesPanel(modifier = Modifier.padding(top = Themes.size.spaceSize16))
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(-1) }
    LazyColumn(
        state = scrollState,
        modifier = modifier
            .onBorder(
                onClick = {},
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize1
            )
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(delta)
                    }
                },
            )
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize36)
    ) {
        itemsIndexed(categories) { index, category ->
            ItemCategory(
                selected = selectedIndex == index,
                category = category,
                onItemSelected = onItemSelected,
                onDisableItem = {
                    selectedIndex = index
                }
            )
        }
    }
}

@Composable
fun HeaderCategoriesPanel(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = modifier
            .onBorder(
                onClick = {},
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize1
            )
            .fillMaxWidth()
            .padding(horizontal = Themes.size.spaceSize36)
            .padding(bottom = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Description(
            description = ID,
            modifier = modifier.width(width = Themes.size.spaceSize36)
        )
        Description(
            description = NAME,
            modifier = modifier.weight(weight = WEIGHT_SIZE_4)
        )
    }
}

@Composable
fun ItemCategory(
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    category: CategoryResponseVO,
    onItemSelected: (CategoryResponseVO) -> Unit = {},
    onDisableItem: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .onClickable {
                onItemSelected(category)
                onDisableItem()
            }
            .background(color = if (selected) Themes.colors.success else Themes.colors.background)
            .fillMaxWidth()
            .padding(vertical = Themes.size.spaceSize16)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Description(
            description = category.id.toString(),
            modifier = modifier.width(width = Themes.size.spaceSize36),
            textAlign = TextAlign.Center
        )
        Description(
            description = category.name,
            modifier = modifier.weight(weight = WEIGHT_SIZE_4)
        )
    }
}
