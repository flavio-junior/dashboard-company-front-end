package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.strings.StringsUtils.ADDRESS
import br.com.digital.store.components.strings.StringsUtils.COMPLEMENT
import br.com.digital.store.components.strings.StringsUtils.DISTRICT
import br.com.digital.store.components.strings.StringsUtils.NUMBER
import br.com.digital.store.components.strings.StringsUtils.STREET
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.description
import br.com.digital.store.composeapp.generated.resources.dialpad
import br.com.digital.store.composeapp.generated.resources.home
import br.com.digital.store.composeapp.generated.resources.personal_places
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun CashRegisterFields(
    itemSelected: String
) {
    when (itemSelected) {
        "Delivery" -> {
            DeliveryAddress()
        }

        "Reserva" -> {

        }

        "Pedido" -> {

        }
    }
}

@Composable
fun DeliveryAddress() {
    Column(verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
        var street: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        var number: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
        var district: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        var complement: String by remember { mutableStateOf(value = EMPTY_TEXT) }
        Description(description = ADDRESS)
        Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
            TextField(
                label = STREET,
                value = street,
                icon = Res.drawable.home,
                onValueChange = {
                    street = it
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
            TextField(
                label = NUMBER,
                value = number.toString(),
                icon = Res.drawable.dialpad,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    number = it.toIntOrNull() ?: NUMBER_ZERO
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)) {
            TextField(
                label = DISTRICT,
                value = district,
                icon = Res.drawable.personal_places,
                onValueChange = {
                    district = it
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
            TextField(
                label = COMPLEMENT,
                value = complement,
                icon = Res.drawable.description,
                onValueChange = {
                    complement = it
                },
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
        }
        AddItemsOrder()
    }
}
