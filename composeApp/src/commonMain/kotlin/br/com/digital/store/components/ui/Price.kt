package br.com.digital.store.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.mask.CurrencyVisualTransformation
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.theme.Themes
import br.com.digital.store.theme.Typography
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

@Composable
fun Price(
    value: String,
    label: String? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    cleanText: Boolean = false,
    message: String = EMPTY_TEXT,
    onGo: () -> Unit = {},
    onCleanText: (Boolean) -> Unit = {}
) {
    var text by remember { mutableStateOf(value) }
    if (cleanText) {
        text = EMPTY_TEXT
        onCleanText(false)
    }
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { newValue ->
                val digitsOnly = newValue.filter { it.isDigit() }
                val decimalValue = digitsOnly.toDoubleOrNull()?.div(100) ?: 0.0
                if (decimalValue <= 1000000.0) {
                    text = digitsOnly
                    onValueChange(decimalValue.toString())
                }
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = CurrencyVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(onGo = { onGo() }),
            singleLine = true,
            label = { Description(description = label ?: PRICE, color = Themes.colors.primary) },
            isError = isError,
            textStyle = Typography(color = Themes.colors.primary).simpleText(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Themes.colors.background,
                cursorColor = Themes.colors.primary,
                focusedIndicatorColor = Themes.colors.primary,
                unfocusedIndicatorColor = Themes.colors.primary
            ),
            shape = RoundedCornerShape(size = Themes.size.spaceSize16)
        )
        IsErrorMessage(isError = isError, message = message)
    }
}
