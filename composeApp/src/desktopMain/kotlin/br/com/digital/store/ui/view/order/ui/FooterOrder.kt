package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Button
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.print
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.status.ObjectStatus
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.components.ui.SelectPrint
import br.com.digital.store.ui.view.components.utils.ThermalPrinter
import br.com.digital.store.ui.view.order.utils.formatItemsToPrint
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import java.io.ByteArrayInputStream

@Composable
fun FooterOrder(
    modifier: Modifier = Modifier,
    orderResponseVO: OrderResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var openPrint: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val thermalPrinter = ThermalPrinter()
    ItemObject(
        modifier = Modifier.padding(top = Themes.size.spaceSize8),
        body = {
            DeleteOrder(
                orderId = orderResponseVO.id,
                modifier = modifier,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh,
                onError = {
                    observer = it
                }
            )
            CloseOrder(
                orderId = orderResponseVO.id,
                fee = orderResponseVO.fee,
                modifier = modifier,
                goToAlternativeRoutes = goToAlternativeRoutes,
                onRefresh = onRefresh,
                onError = {
                    observer = it
                }
            )
            Button(
                icon = Res.drawable.print,
                onClick = {
                    val allDelivered =
                        orderResponseVO.objects?.all { it.status == ObjectStatus.DELIVERED }
                    allDelivered?.let {
                        if (!it) {
                            openPrint = true
                        }
                    }
                }
            )
            val textToDisplay = formatItemsToPrint(order = orderResponseVO)
            val inputStream = ByteArrayInputStream(textToDisplay.toByteArray(Charsets.UTF_8))
            if (openPrint) {
                SelectPrint(
                    onDismissRequest = {
                        openPrint = false
                        onRefresh()
                    },
                    onConfirmation = { printerName ->
                        openPrint = false
                        thermalPrinter.printInputStream(
                            inputStream = inputStream,
                            printerName = printerName
                        )
                        onRefresh()
                    }
                )
            }
        }
    )
    IsErrorMessage(isError = observer.second, message = observer.third)
}
