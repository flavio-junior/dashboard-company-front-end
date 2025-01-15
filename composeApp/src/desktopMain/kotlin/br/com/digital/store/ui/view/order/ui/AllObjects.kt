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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.theme.Themes
import kotlinx.coroutines.launch

@Composable
fun AllObjects(
    objectSelected: List<ObjectRequestDTO>,
    verifyObjects: Boolean,
    objectsToSave: (List<ObjectRequestDTO>) -> Unit = {},
    onItemSelected: (ObjectRequestDTO) -> Unit = {}
) {
    val listObjectsSelected = remember { mutableStateListOf<ObjectRequestDTO>() }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(value = -1) }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        state = scrollState,
        modifier = Modifier
            .background(color = Themes.colors.background)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                },
            )
    ) {
        itemsIndexed(items = objectSelected) { index, objectResult ->
            ItemObject(
                body = {
                    CardObjectSelect(
                        objectRequestDTO = objectResult,
                        selected = selectedIndex == index,
                        verifyObject = verifyObjects,
                        onItemSelected = { objectResult ->
                            onItemSelected(objectResult)
                        },
                        onDisableItem = {
                            selectedIndex = index
                        },
                        onResult = {
                            listObjectsSelected.add(it)
                        }
                    )
                }
            )
        }
    }
    objectsToSave(listObjectsSelected)
}
