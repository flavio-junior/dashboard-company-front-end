package br.com.digital.store.features.report.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import br.com.digital.store.components.strings.StringsUtils.AUTHOR
import br.com.digital.store.components.strings.StringsUtils.DATE
import br.com.digital.store.components.strings.StringsUtils.HOUR
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.OPTIONS
import br.com.digital.store.components.strings.StringsUtils.RESUME
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.visibility
import br.com.digital.store.features.report.data.vo.ReportResponseVO
import br.com.digital.store.features.report.data.vo.ReportsResponseVO
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_4
import br.com.digital.store.utils.NumbersUtils
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.onClickable
import kotlinx.coroutines.launch

@Composable
fun ListReports(
    modifier: Modifier = Modifier,
    checkouts: ReportsResponseVO,
    onItemSelected: (ReportResponseVO) -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HeaderReportsPanel(modifier = Modifier.padding(top = Themes.size.spaceSize16))
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var selectedIndex by remember { mutableStateOf(value = -1) }
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize16))
        LazyColumn(
            state = scrollState,
            modifier = modifier
                .onBorder(
                    onClick = {},
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2
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
            itemsIndexed(checkouts.content) { index, checkoutResponseVO ->
                ItemReport(
                    index = index,
                    selected = selectedIndex == index,
                    report = checkoutResponseVO,
                    onItemSelected = onItemSelected,
                    onDisableItem = {
                        selectedIndex = index
                    }
                )
            }
        }
    }
}

@Composable
fun HeaderReportsPanel(
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
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = DATE,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = HOUR,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = RESUME,
            modifier = modifier.weight(weight = WEIGHT_SIZE_4),
            textAlign = TextAlign.Center
        )
        Description(
            description = AUTHOR,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            maxLines = NumbersUtils.NUMBER_ONE,
            textAlign = TextAlign.Center
        )
        Description(
            description = OPTIONS,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ItemReport(
    index: Int,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    report: ReportResponseVO,
    onItemSelected: (ReportResponseVO) -> Unit = {},
    onDisableItem: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .onClickable {
                onDisableItem()
            }
            .background(color = if (selected) ITEM_SELECTED else Themes.colors.background)
            .fillMaxWidth()
            .padding(vertical = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Description(
            description = report.id.toString(),
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center,
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        Description(
            description = report.date ?: EMPTY_TEXT,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        Description(
            description = report.hour ?: EMPTY_TEXT,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        Description(
            description = report.resume ?: EMPTY_TEXT,
            maxLines = NumbersUtils.NUMBER_ONE,
            modifier = modifier.weight(weight = WEIGHT_SIZE_4),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        Description(
            description = report.author ?: EMPTY_TEXT,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        IconDefault(
            icon = Res.drawable.visibility,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            backgroundColor = if (selected) ITEM_SELECTED else Themes.colors.background,
            tint = if (selected) Themes.colors.background else Themes.colors.primary,
            onClick = {
                onItemSelected(report)
            }
        )
    }
}
