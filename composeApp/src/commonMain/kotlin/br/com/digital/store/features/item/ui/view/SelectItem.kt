package br.com.digital.store.features.item.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import br.com.digital.store.components.strings.StringsUtils.CANCEL
import br.com.digital.store.components.strings.StringsUtils.CONFIRM
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Search
import br.com.digital.store.components.ui.SimpleButton
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.item.data.dto.ItemResponseDTO
import br.com.digital.store.features.item.ui.viewmodel.ItemViewModel
import br.com.digital.store.features.item.ui.viewmodel.ResetItem
import br.com.digital.store.features.item.utils.ItemsUtils.ADD_ITEMS
import br.com.digital.store.features.item.utils.ItemsUtils.NO_ITEMS_SELECTED
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SelectItems(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (List<ItemResponseDTO>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: ItemViewModel = getKoin().get()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .size(size = Themes.size.spaceSize500)
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            val items = remember { mutableStateListOf<ItemResponseDTO>() }
            var itemsSelected = remember { mutableStateListOf<ItemResponseDTO>() }
            Title(title = ADD_ITEMS)
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findItemByName(name = name)
                }
            )
            SelectItems(
                items = items,
                itemsSelected = itemsSelected,
                onResult = {
                    itemsSelected = it.toMutableStateList()
                }
            )
            ListItemsAvailable(items = itemsSelected)
            FooterSelectItems(
                onDismissRequest = {
                    viewModel.resetItem(reset = ResetItem.FIND_BY_NAME)
                    onDismissRequest()
                },
                onConfirmation = {
                    if (itemsSelected.isNotEmpty()) {
                        viewModel.resetItem(reset = ResetItem.FIND_BY_NAME)
                        onConfirmation(itemsSelected)
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_ITEMS_SELECTED)
                    }
                }
            )
            ObserveNetworkStateHandlerFindItemByName(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    items.clear()
                    items.addAll(it)
                }
            )
        }
    }
}

@Composable
private fun SelectItems(
    items: List<ItemResponseDTO>,
    itemsSelected: MutableList<ItemResponseDTO>,
    onResult: (List<ItemResponseDTO>) -> Unit = {}
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
        items(items) { items ->
            Tag(
                text = items.name,
                value = items,
                enabled = itemsSelected.contains(items),
                onCheck = { isChecked ->
                    if (isChecked) {
                        itemsSelected.add(items)
                    } else {
                        itemsSelected.remove(items)
                    }
                    onResult(itemsSelected)
                }
            )
        }
    }
}

@Composable
private fun ListItemsAvailable(
    items: List<ItemResponseDTO>
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
        items(items) { items ->
            Description(description = items.name)
        }
    }
}

@Composable
private fun FooterSelectItems(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
    ) {
        SimpleButton(
            onClick = onDismissRequest,
            label = CANCEL,
            background = Themes.colors.error,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        SimpleButton(
            onClick = onConfirmation,
            label = CONFIRM,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindItemByName(
    viewModel: ItemViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<ItemResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<ItemResponseDTO>> by remember { viewModel.findItemByName }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllItems()
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
