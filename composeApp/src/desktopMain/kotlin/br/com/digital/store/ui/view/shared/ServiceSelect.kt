package br.com.digital.store.ui.view.shared

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.DASHBOARD
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.Title
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.ItemService
import br.com.digital.store.utils.changeColor
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.onClickable
import kotlinx.coroutines.launch

@Composable
fun Services(
    modifier: Modifier = Modifier,
    label: String,
    services: List<ItemService> = availableServices,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by remember { mutableStateOf(value = -1) }
    LazyColumn(
        state = scrollState,
        modifier = modifier
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(delta)
                    }
                },
            )
            .padding(
                start = Themes.size.spaceSize16,
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize8,
                end = Themes.size.spaceSize16
            ),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        items(services) { item ->
            if (item.label == label) Service(
                enabled = true,
                service = item,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            ) else Service(
                service = item,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    }
}

@Composable
fun Service(
    enabled: Boolean = false,
    service: ItemService,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    val goToNavigation = {
        if (service.route == DASHBOARD) goToBackScreen() else goToNextScreen(service.route)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
            .onBorder(
                onClick = goToNavigation,
                width = Themes.size.spaceSize2,
                spaceSize = Themes.size.spaceSize8,
                color = Themes.colors.primary
            )
            .changeColor(enabled = enabled)
            .width(width = Themes.size.spaceSize300)
            .padding(all = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            backgroundColor = if (enabled) ITEM_SELECTED else Themes.colors.secondary,
            tint = if (enabled) Themes.colors.background else ITEM_SELECTED,
            icon = service.icon,
            onClick = goToNavigation
        )
        Title(
            title = service.label,
            modifier = Modifier.onClickable(onClick = goToNavigation),
            color = if (enabled) Themes.colors.background else ITEM_SELECTED
        )
    }
}
