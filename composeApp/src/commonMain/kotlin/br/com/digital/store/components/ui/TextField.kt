package br.com.digital.store.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    if (icon != null) {
        TextFieldWithIcon(
            modifier = modifier,
            enabled = enabled,
            label = label,
            value = value,
            icon = icon,
            keyboardType = keyboardType,
            imeAction = imeAction,
            isError = isError,
            message = message,
            onValueChange = onValueChange,
            onGo = onGo
        )
    } else {
        TextFieldWithoutIcon(
            modifier = modifier,
            enabled = enabled,
            label = label,
            value = value,
            keyboardType = keyboardType,
            imeAction = imeAction,
            isError = isError,
            message = message,
            onValueChange = onValueChange,
            onGo = onGo
        )
    }
}

@Composable
private fun TextFieldWithIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    value: String,
    icon: DrawableResource,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        OutlinedTextField(
            enabled = enabled,
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            singleLine = true,
            label = {
                Description(description = label, color = Themes.colors.primary)
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(resource = icon),
                    contentDescription = label,
                    tint = Themes.colors.primary
                )

            },
            textStyle = Typography(color = Themes.colors.primary).simpleText(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onGo = { onGo() }
            ),
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

@Composable
private fun TextFieldWithoutIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        OutlinedTextField(
            enabled = enabled,
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            singleLine = true,
            label = {
                InfoText(text = label, color = Themes.colors.primary)
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography(color = Themes.colors.primary).simpleText(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onGo = { onGo() }
            ),
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