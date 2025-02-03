package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.TOTAL
import br.com.digital.store.components.strings.StringsUtils.TYPE
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.ResourceUnavailable
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.data.vo.OverviewResponseVO
import br.com.digital.store.features.order.domain.factory.objectFactory
import br.com.digital.store.features.order.domain.factory.typeOrderFactory
import br.com.digital.store.features.order.utils.OrderUtils.DETAILS_ITEM
import br.com.digital.store.features.order.utils.OrderUtils.RESUME_ITEM
import br.com.digital.store.features.order.utils.OrderUtils.RESUME_SELECTED
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.store.utils.formatterMaskToMoney

@Composable
fun DetailsItem(
    orderId: Long,
    objectResponseVO: ObjectResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    if (orderId > NUMBER_ZERO && objectResponseVO.id > NUMBER_ZERO) {
        Column(
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            HeaderDetailsItem(
                orderId = orderId,
                objectResponseVO = objectResponseVO,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )
            OverviewItem(
                orderId = orderId,
                objectId = objectResponseVO.id,
                overview = objectResponseVO.overview ?: emptyList(),
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )
        }
    } else {
        ResourceUnavailable(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun HeaderDetailsItem(
    orderId: Long,
    objectResponseVO: ObjectResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Description(description = "$DETAILS_ITEM:")
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        TextField(
            enabled = false,
            label = ID,
            value = objectResponseVO.id.toString(),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = NAME,
            value = objectResponseVO.name,
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = TYPE,
            value = typeOrderFactory(type = objectResponseVO.type),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = STATUS,
            value = objectFactory(status = objectResponseVO.status),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        TextField(
            enabled = false,
            label = PRICE,
            value = formatterMaskToMoney(price = objectResponseVO.price),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = QUANTITY,
            value = objectResponseVO.quantity.toString(),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = TOTAL,
            value = formatterMaskToMoney(price = objectResponseVO.total),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        RemoveObject(
            orderId = orderId,
            objectId = objectResponseVO.id,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
    }
}

@Composable
private fun OverviewItem(
    orderId: Long,
    objectId: Long,
    overview: List<OverviewResponseVO>,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var overviewResponseVO: OverviewResponseVO by remember {
        mutableStateOf(value = OverviewResponseVO())
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = RESUME_ITEM)
        ListOverviews(
            overviews = overview,
            onItemSelected = {
                overviewResponseVO = it
            }
        )
        FooterItem(
            orderId = orderId,
            objectId = objectId,
            overviewId = overviewResponseVO.id,
            status = objectFactory(status = overviewResponseVO.status),
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
fun FooterItem(
    modifier: Modifier = Modifier,
    orderId: Long,
    objectId: Long,
    overviewId: Long,
    status: String,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Description(description = RESUME_SELECTED)
    ItemObject(
        modifier = Modifier.padding(top = Themes.size.spaceSize8),
        body = {
            IncrementOverview(
                modifier = modifier,
                orderId = orderId,
                objectId = objectId,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh
            )
            UpdateStatusOverview(
                orderId = orderId,
                objectId = objectId,
                overviewId = overviewId,
                modifier = modifier,
                status = status,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh,
                onError = {
                    observer = it
                }
            )
            RemoveOverview(
                orderId = orderId,
                objectId = objectId,
                overviewId = overviewId,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh,
                onError = {
                    observer = it
                }
            )
        }
    )
    IsErrorMessage(isError = observer.second, message = observer.third)
    Spacer(modifier = Modifier.height(height = Themes.size.spaceSize64))
}
