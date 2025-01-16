package br.com.digital.store.features.components

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import br.com.digital.store.R
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.theme.Themes
import br.com.digital.store.theme.Typography
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.onClickable

@Composable
fun TextPassword(
    label: String,
    value: String,
    imeAction: ImeAction = ImeAction.Go,
    textColor: Color = Themes.colors.primary,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit = {}
) {
    var passwordHidden by rememberSaveable { mutableStateOf(value = true) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = Typography(color = Themes.colors.primary).simpleText(),
        singleLine = true,
        label = {
            Description(description = label)
        },
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.lock),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.onClickable {
                    passwordHidden = !passwordHidden
                }
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onGo = {
            onGo()
        }),
        trailingIcon = {
            Icon(
                painter = painterResource(id = if (passwordHidden) R.drawable.visibility else R.drawable.visibility_off),
                contentDescription = if (passwordHidden) "Show password" else "Hide password",
                tint = textColor
            )
        },
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
