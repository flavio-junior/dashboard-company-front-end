package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.PrintDocument
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

@Composable
fun FooterOrder(
    modifier: Modifier = Modifier,
    orderId: Long,
    status: String,
    type: TypeOrder? = null,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    ItemObject(
        modifier = Modifier.padding(top = Themes.size.spaceSize8),
        body = {
            if (type != null) {
                UpdateStatusDelivery(
                    orderId = orderId,
                    modifier = modifier,
                    status = status,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = onRefresh,
                    onError = {
                        observer = it
                    }
                )
            }
            DeleteOrder(
                orderId = orderId,
                modifier = modifier,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh,
                onError = {
                    observer = it
                }
            )
            CloseOrder(
                orderId = orderId,
                modifier = modifier,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh,
                onError = {
                    observer = it
                }
            )
            PrintDocument()
        }
    )
    IsErrorMessage(isError = observer.second, message = observer.third)
}
