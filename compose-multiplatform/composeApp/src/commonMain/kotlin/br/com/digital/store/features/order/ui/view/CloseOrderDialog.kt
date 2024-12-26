package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import br.com.digital.store.components.strings.StringsUtils.ALERT
import br.com.digital.store.components.strings.StringsUtils.CANCEL
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.SimpleButton
import br.com.digital.store.components.ui.Title
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.brand_awareness
import br.com.digital.store.features.order.domain.factory.typePaymentFactory
import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.utils.OrderUtils.CLOSE_ORDER
import br.com.digital.store.features.order.utils.OrderUtils.SELECTED_TYPE_PAYMENT
import br.com.digital.store.features.order.utils.OrderUtils.TYPE_OF_PAYMENT
import br.com.digital.store.features.order.utils.typePayment
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder

@Composable
fun ClosedOrderDialog(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (PaymentType) -> Unit = {}
) {
    var itemSelected: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var observer: Pair<Boolean, String> by remember {
        mutableStateOf(value = Pair(first = false, second = EMPTY_TEXT))
    }
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
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconDefault(
                    icon = Res.drawable.brand_awareness,
                    size = Themes.size.spaceSize48
                )
                Title(
                    title = ALERT,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    textAlign = TextAlign.Start
                )
            }
            Description(
                description = SELECTED_TYPE_PAYMENT,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Themes.size.spaceSize8)
            )
            DropdownMenu(
                selectedValue = itemSelected,
                items = typePayment,
                label = TYPE_OF_PAYMENT,
                isError = observer.first,
                onValueChangedEvent = {
                    itemSelected = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            IsErrorMessage(isError = observer.first, message = observer.second)
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
                    onClick = {
                        if (itemSelected.isEmpty()) {
                            observer = Pair(first = true, second = SELECTED_TYPE_PAYMENT)
                        } else {
                            observer = Pair(first = false, second = EMPTY_TEXT)
                            typePaymentFactory(payment = itemSelected)?.let { onConfirmation(it) }
                        }
                    },
                    label = CLOSE_ORDER,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
            }
        }
    }
}
