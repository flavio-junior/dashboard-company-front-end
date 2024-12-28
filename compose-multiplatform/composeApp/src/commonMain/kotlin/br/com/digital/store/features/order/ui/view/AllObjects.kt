package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.theme.Themes
import kotlinx.coroutines.launch

@Composable
fun AllObjects(
    objectSelected: List<ObjectRequestDTO>,
    verifyObjects: Boolean,
    objectsToSave: (List<ObjectRequestDTO>) -> Unit = {}
) {
    val listObjectsSelected = remember { mutableStateListOf<ObjectRequestDTO>() }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
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
        items(items = objectSelected) { objectResult ->
            ItemObject(
                body = {
                    CardObjectSelect(
                        objectRequestDTO = objectResult,
                        verifyObject = verifyObjects,
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
