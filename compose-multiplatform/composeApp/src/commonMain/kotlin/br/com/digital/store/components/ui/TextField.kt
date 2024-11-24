package br.com.digital.store.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.close
import br.com.digital.store.strings.StringsUtils.CLEAN
import br.com.digital.store.theme.Themes
import br.com.digital.store.theme.Typography
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    value: String,
    icon: DrawableResource? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit = {}
) {
    var showClearButton by remember { mutableStateOf(value = false) }
    if (enabled) {
        if (value.isNotBlank()) showClearButton = true
    }
    OutlinedTextField(
        enabled = enabled,
        value = value,
        onValueChange = {
            onValueChange(it)
            showClearButton = it.isNotBlank()
        },
        singleLine = true,
        label = {
            Description(description = label, color = Themes.colors.primary)
        },
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            icon?.let {
                Icon(
                    painter = painterResource(resource =  it),
                    contentDescription = label,
                    tint = Themes.colors.primary
                )
            }
        },
        trailingIcon = {
            if (showClearButton) {
                IconDefault(
                    icon = Res.drawable.close,
                    contentDescription = CLEAN,
                    onClick = {
                        onValueChange(EMPTY_TEXT)
                    }
                )
            }
        },
        textStyle = Typography(color = Themes.colors.primary).simpleText(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onGo = {
            onGo()
        }),
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
