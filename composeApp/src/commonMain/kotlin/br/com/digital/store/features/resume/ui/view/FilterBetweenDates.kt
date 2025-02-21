package br.com.digital.store.features.resume.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import br.com.digital.store.components.strings.StringsUtils.DATE
import br.com.digital.store.components.strings.StringsUtils.DAY
import br.com.digital.store.components.strings.StringsUtils.DAYS
import br.com.digital.store.components.strings.StringsUtils.END_DATE
import br.com.digital.store.components.strings.StringsUtils.SEARCH
import br.com.digital.store.components.strings.StringsUtils.START_DATE
import br.com.digital.store.components.ui.DropdownMenu
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.reservation
import br.com.digital.store.features.resume.utils.selectTypeDate
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils
import br.com.digital.store.utils.formatDateInput
import br.com.digital.store.utils.onBorder

@Composable
fun FilterBetweenDates(
    onDismissRequest: () -> Unit = {},
    onDateSelected: (String) -> Unit = {},
    onDatesSelected: (Pair<String, String>) -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .width(width = Themes.size.spaceSize500)
                .padding(all = Themes.size.spaceSize36),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var itemSelected: String by remember { mutableStateOf(value = DAY) }
            var date: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var start: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var end: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var findResumes = {}
            DropdownMenu(
                selectedValue = itemSelected,
                items = selectTypeDate,
                label = SEARCH,
                onValueChangedEvent = {
                    itemSelected = it
                },
                modifier = Modifier.fillMaxWidth()
            )
            when (itemSelected) {
                DAY -> {
                    TextField(
                        label = DATE,
                        icon = Res.drawable.reservation,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Go,
                        value = date,
                        onValueChange = {
                            date = formatDateInput(input = it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        onGo = {
                            if (date.length == NumbersUtils.NUMBER_TEN) {
                                onDateSelected(date)
                            }
                        }
                    )
                    findResumes = {
                        onDateSelected(date)
                    }
                }

                DAYS -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
                    ) {
                        TextField(
                            label = START_DATE,
                            icon = Res.drawable.reservation,
                            keyboardType = KeyboardType.Number,
                            value = start,
                            onValueChange = {
                                start = formatDateInput(input = it)
                            },
                            modifier = Modifier
                                .weight(weight = WEIGHT_SIZE),
                            onGo = {
                                if (start.length == NumbersUtils.NUMBER_TEN) {
                                    onDateSelected(start)
                                }
                            }
                        )
                        TextField(
                            label = END_DATE,
                            icon = Res.drawable.reservation,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Go,
                            value = end,
                            onValueChange = {
                                end = formatDateInput(input = it)
                            },
                            modifier = Modifier
                                .weight(weight = WEIGHT_SIZE),
                            onGo = {
                                if (start.length == NumbersUtils.NUMBER_TEN && end.length == NumbersUtils.NUMBER_TEN) {
                                    onDatesSelected(Pair(first = start, second = end))
                                }
                            }
                        )
                    }
                    findResumes = {
                        onDatesSelected(Pair(first = start, second = end))
                    }
                }
            }
            LoadingButton(
                label = SEARCH,
                onClick = findResumes
            )
        }
    }
}
