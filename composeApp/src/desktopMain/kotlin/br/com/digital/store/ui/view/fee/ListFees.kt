package br.com.digital.store.ui.view.fee

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
import br.com.digital.store.components.strings.StringsUtils.ADD_DAYS
import br.com.digital.store.components.strings.StringsUtils.ASSIGNED
import br.com.digital.store.components.strings.StringsUtils.DELETE_FEE
import br.com.digital.store.components.strings.StringsUtils.LIST_FEES
import br.com.digital.store.components.strings.StringsUtils.NUMBER
import br.com.digital.store.components.strings.StringsUtils.OPTIONS
import br.com.digital.store.components.strings.StringsUtils.PERCENTAGE
import br.com.digital.store.components.strings.StringsUtils.WITH_DAYS_OF_WEEK
import br.com.digital.store.components.strings.StringsUtils.YES
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Title
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.delete
import br.com.digital.store.features.fee.data.vo.FeeResponseVO
import br.com.digital.store.features.fee.domain.factory.functionFactory
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import br.com.digital.store.features.fee.ui.viewmodel.ResetFee
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.onClickable
import kotlinx.coroutines.launch

@Composable
fun ListFees(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit,
    modifier: Modifier = Modifier,
    fees: List<FeeResponseVO>,
    onItemSelected: (FeeResponseVO) -> Unit = {},
    registerDays: (Long) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Title(title = LIST_FEES)
        HeaderFeesPanel(modifier = Modifier.padding(top = Themes.size.spaceSize16))
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
            itemsIndexed(fees) { index, fee ->
                ItemFee(
                    viewModel = viewModel,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    index = index,
                    selected = selectedIndex == index,
                    fee = fee,
                    onItemSelected = onItemSelected,
                    registerDays = registerDays,
                    onDisableItem = {
                        selectedIndex = index
                    },
                    onSuccess = onSuccess
                )
            }
        }
    }
}

@Composable
fun HeaderFeesPanel(
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
            description = NUMBER,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = PERCENTAGE,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = ASSIGNED,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = WITH_DAYS_OF_WEEK,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
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
fun ItemFee(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    index: Int,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    fee: FeeResponseVO,
    onItemSelected: (FeeResponseVO) -> Unit = {},
    registerDays: (Long) -> Unit = {},
    onDisableItem: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    var deleteFee: Boolean by remember { mutableStateOf(value = false) }
    Row(
        modifier = modifier
            .onClickable {
                onDisableItem()
                onItemSelected(fee)
            }
            .background(color = if (selected) ITEM_SELECTED else Themes.colors.background)
            .fillMaxWidth()
            .padding(vertical = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var add = index
        add++
        Description(
            description = add.toString(),
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center,
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        Description(
            description = "${fee.percentage} %",
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        Description(
            description = functionFactory(function = fee.assigned),
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        if (fee.days?.isNotEmpty() == true) {
            Description(
                description = YES,
                modifier = modifier.weight(weight = WEIGHT_SIZE),
                color = if (selected) Themes.colors.background else Themes.colors.primary,
                textAlign = TextAlign.Center
            )
        } else {
            LoadingButton(
                label = ADD_DAYS,
                modifier = modifier
                    .padding(end = Themes.size.spaceSize8)
                    .weight(weight = WEIGHT_SIZE),
                onClick = {
                    registerDays(fee.id)
                }
            )
        }
        IconDefault(
            icon = Res.drawable.delete,
            backgroundColor = if (selected) ITEM_SELECTED else Themes.colors.background,
            tint = if (selected) Themes.colors.background else Themes.colors.primary,
            modifier = modifier
                .align(alignment = Alignment.CenterVertically)
                .weight(weight = WEIGHT_SIZE),
            onClick = {
                deleteFee = true
            }
        )
        if (deleteFee) {
            Alert(
                label = DELETE_FEE,
                onDismissRequest = {
                    deleteFee = false
                },
                onConfirmation = {
                    viewModel.deleteFee(feeId = fee.id)
                    deleteFee = false
                }
            )
        }
    }
    ObserveNetworkStateHandlerDeleteFee(
        viewModel = viewModel,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onError = {},
        onSuccess = {
            onSuccess()
        }
    )
}

@Composable
private fun ObserveNetworkStateHandlerDeleteFee(
    viewModel: FeeViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteFee }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it ?: EMPTY_TEXT))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetFee(reset = ResetFee.DELETE_FEE)
            onSuccess()
        }
    )
}
