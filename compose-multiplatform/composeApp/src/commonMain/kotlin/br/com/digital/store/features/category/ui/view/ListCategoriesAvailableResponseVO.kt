package br.com.digital.store.features.category.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CATEGORIES
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.features.category.data.vo.CategoryResponseVO
import br.com.digital.store.theme.Themes
import kotlinx.coroutines.launch

@Composable
fun ListCategoriesAvailableResponseVO(
    categories: List<CategoryResponseVO>? = null
) {
    Description(description = "$CATEGORIES:")
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
            state = scrollState,
            modifier = Modifier
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    }
                )
        ) {
            categories?.let {
                items(it) { category ->
                    Tag(
                        text = category.name,
                        value = category,
                        enabled = false
                    )
                }
            }
        }
    }
}