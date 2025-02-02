package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import br.com.digital.store.components.strings.StringsUtils.ALERT
import br.com.digital.store.components.strings.StringsUtils.CANCEL
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.Price
import br.com.digital.store.components.ui.SimpleButton
import br.com.digital.store.components.ui.Title
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.brand_awareness
import br.com.digital.store.features.fee.data.vo.FeeResponseOrderVO
import br.com.digital.store.features.item.utils.checkPriceIsEqualsZero
import br.com.digital.store.features.order.data.dto.PaymentRequestDTO
import br.com.digital.store.features.order.domain.factory.typePaymentFactory
import br.com.digital.store.features.order.utils.OrderUtils.CLOSE_ORDER
import br.com.digital.store.features.order.utils.OrderUtils.DIGIT_ONE_VALUE_TO_DISCOUNT
import br.com.digital.store.features.order.utils.OrderUtils.SELECTED_TYPE_PAYMENT
import br.com.digital.store.features.order.utils.OrderUtils.TYPE_OF_PAYMENT
import br.com.digital.store.features.order.utils.OrderUtils.VALUE_DISCOUNT
import br.com.digital.store.features.order.utils.typePayment
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.ZERO_DOUBLE
import br.com.digital.store.utils.onBorder

@Composable
fun ClosedOrderDialog(
    fee: FeeResponseOrderVO? = null,
    onDismissRequest: () -> Unit = {},
    onConfirmation: (PaymentRequestDTO) -> Unit = {}
) {
    var itemSelected: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var applyDiscount: Boolean by remember { mutableStateOf(value = false) }
    var valueDiscount: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
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
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
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
            ApplyDiscount(
                enabled = applyDiscount,
                onError = observer.first,
                priceResult = {
                    valueDiscount = it
                },
                applyDiscount = {
                    applyDiscount = it
                }
            )
            //if (fee != null) {
             Description(description = "Pedido com taxa!")
             Description(description = fee?.percentage.toString())
           // }
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
                        } else if (itemSelected.isNotEmpty() && applyDiscount && checkPriceIsEqualsZero(
                                price = valueDiscount.toDouble()
                            )
                        ) {
                            observer = Pair(first = true, second = DIGIT_ONE_VALUE_TO_DISCOUNT)
                        } else {
                            observer = Pair(first = false, second = EMPTY_TEXT)
                            onConfirmation(
                                typePaymentFactory(
                                    payment = itemSelected,
                                    discount = applyDiscount,
                                    value = valueDiscount.toDouble()
                                )
                            )
                        }
                    },
                    label = CLOSE_ORDER,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE)
                )
            }
        }
    }
}

@Composable
fun ApplyDiscount(
    enabled: Boolean,
    onError: Boolean,
    priceResult: (String) -> Unit = {},
    applyDiscount: (Boolean) -> Unit = {}
) {
    var isEnabled by remember { mutableStateOf(enabled) }
    var price: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
    ) {
        Checkbox(
            colors = CheckboxDefaults.colors(
                checkedColor = Themes.colors.primary,
                uncheckedColor = Color.Gray,
                checkmarkColor = Themes.colors.background
            ),
            checked = isEnabled,
            onCheckedChange = { checked ->
                isEnabled = checked
                applyDiscount(checked)
            },
            modifier = Modifier
                .scale(scale = 2f)
                .size(size = Themes.size.spaceSize48)
        )
        Price(
            enabled = isEnabled,
            label = VALUE_DISCOUNT,
            value = price,
            isError = onError,
            onValueChange = {
                price = it
                priceResult(it)
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}
