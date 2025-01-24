package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.SELECTED_ITEMS
import br.com.digital.store.components.ui.Description
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.launch

@Composable
fun BodyCard(
    label: String = SELECTED_ITEMS,
    title: Boolean = true,
    objectsToSave: List<ObjectRequestDTO>,
    verifyObjects: Boolean = false,
    isError: Boolean = false,
    onItemSelected: (ObjectRequestDTO) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(value = -1) }
    if (title) {
        Description(description = label)
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        state = scrollState,
        modifier = Modifier
            .background(color = Themes.colors.background)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(value = -delta)
                    }
                },
            )
    ) {
        itemsIndexed(items = objectsToSave) { index, objectResult ->
            val quantity = objectsToSave.find { it.name == objectResult.name }?.quantity
                ?: NUMBER_ZERO
            CardObjectSelect(
                objectRequestDTO = objectResult,
                selected = selectedIndex == index,
                isError = isError,
                verifyObject = verifyObjects,
                quantity = quantity,
                onQuantityChange = {
                    objectResult.quantity = it
                },
                onItemSelected = onItemSelected,
                onDisableItem = {
                    selectedIndex = index
                },
            )
        }
    }
}
