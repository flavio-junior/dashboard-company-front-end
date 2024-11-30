package br.com.digital.store.features.product.ui.view

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
import br.com.digital.store.components.strings.StringsUtils.CATEGORIES
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.NUMBER
import br.com.digital.store.components.strings.StringsUtils.OPTIONS
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.visibility
import br.com.digital.store.features.product.data.vo.ProductResponseVO
import br.com.digital.store.features.product.data.vo.ProductsResponseVO
import br.com.digital.store.theme.CommonColors.ITEM_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.NumbersUtils.NUMBER_ONE
import br.com.digital.store.utils.formatterMaskToMoney
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.onClickable
import kotlinx.coroutines.launch

@Composable
fun ListProducts(
    modifier: Modifier = Modifier,
    content: ProductsResponseVO,
    onItemSelected: (ProductResponseVO) -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HeaderProductsPanel(modifier = Modifier.padding(top = Themes.size.spaceSize16))
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
            itemsIndexed(content.content) { index, product ->
                ItemProduct(
                    index = index,
                    selected = selectedIndex == index,
                    product = product,
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
fun HeaderProductsPanel(
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
            description = NAME,
            modifier = modifier.weight(weight = WEIGHT_SIZE_2),
            textAlign = TextAlign.Center
        )
        Description(
            description = CATEGORIES,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = PRICE,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            description = QUANTITY,
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
fun ItemProduct(
    index: Int,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    product: ProductResponseVO,
    onItemSelected: (ProductResponseVO) -> Unit = {},
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
        var add = index
        add++
        Description(
            description = add.toString(),
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center,
            color = if (selected) Themes.colors.background else Themes.colors.primary
        )
        Description(
            description = product.name,
            maxLines = NUMBER_ONE,
            modifier = modifier.weight(weight = WEIGHT_SIZE_2),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        Description(
            description = product.categories?.size.toString(),
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        Description(
            description = formatterMaskToMoney(product.price),
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            color = if (selected) Themes.colors.background else Themes.colors.primary,
            textAlign = TextAlign.Center
        )
        Description(
            description = product.quantity.toString(),
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
                onItemSelected(product)
            }
        )
    }
}
