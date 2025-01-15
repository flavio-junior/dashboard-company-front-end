package br.com.digital.store.ui.view.components.ui

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
import br.com.digital.store.components.strings.StringsUtils.SELECTED_PRINT
import br.com.digital.store.components.strings.StringsUtils.SELECT_PRINT
import br.com.digital.store.components.strings.StringsUtils.WARNING
import br.com.digital.store.components.ui.Button
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.Title
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.brand_awareness
import br.com.digital.store.composeapp.generated.resources.print
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.components.utils.ThermalPrinter
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder

@Composable
fun SelectPrint(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (String) -> Unit = {}
) {
    var itemSelected: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var observer: Pair<Boolean, String> by remember {
        mutableStateOf(value = Pair(first = false, second = EMPTY_TEXT))
    }
    val thermalPrinter = ThermalPrinter()
    val allPrinters = thermalPrinter.getPrints()
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
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconDefault(
                    icon = Res.drawable.brand_awareness,
                    size = Themes.size.spaceSize48
                )
                Title(
                    title = WARNING,
                    modifier = Modifier.weight(weight = WEIGHT_SIZE),
                    textAlign = TextAlign.Start
                )
            }
            Description(
                description = "$SELECT_PRINT:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Themes.size.spaceSize8)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DropdownMenu(
                    selectedValue = itemSelected,
                    items = allPrinters,
                    label = SELECTED_PRINT,
                    isError = observer.first,
                    onValueChangedEvent = {
                        itemSelected = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = WEIGHT_SIZE)
                )
                Button(
                    onClick = {
                        if (itemSelected.isNotEmpty()) {
                            observer = Pair(false, EMPTY_TEXT)
                            onConfirmation(itemSelected)
                        } else {
                            observer = Pair(true, "$SELECT_PRINT!")
                        }
                    },
                    icon = Res.drawable.print
                )
            }
            IsErrorMessage(isError = observer.first, message = observer.second)
        }
    }
}
