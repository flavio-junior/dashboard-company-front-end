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
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.launch

data class BodyObject(
    val name: String,
    val quantity: Int
)

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
    val contentObjects = remember { mutableStateListOf<BodyObject>() }

    fun updateBodyObject(name: String, newQuantity: Int) {
        val existingIndex = contentObjects.indexOfFirst { it.name == name }
        if (existingIndex != -1) {
            contentObjects[existingIndex] = contentObjects[existingIndex].copy(quantity = newQuantity)
        } else {
            contentObjects.add(BodyObject(name = name, quantity = newQuantity))
        }
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
                        scrollState.scrollBy(-delta)
                    }
                },
            )
    ) {
        itemsIndexed(items = objectSelected) { index, objectResult ->
            val quantity = contentObjects.find { it.name == objectResult.name }?.quantity
                ?: NUMBER_ZERO
            ItemObject(
                body = {
                    CardObjectSelect(
                        objectRequestDTO = objectResult,
                        quantity = quantity,
                        selected = selectedIndex == index,
                        verifyObject = verifyObjects,
                        onItemSelected = { objectResult ->
                            onItemSelected(objectResult)
                        },
                        onQuantityChange = { newQuantity ->
                            updateBodyObject(objectResult.name, newQuantity)
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
