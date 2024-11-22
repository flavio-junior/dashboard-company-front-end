package br.com.digital.store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.components.ui.TextPassword
import br.com.digital.store.strings.StringsUtils.CREATE_ONE_ACCOUNT
import br.com.digital.store.strings.StringsUtils.EMAIL
import br.com.digital.store.strings.StringsUtils.ENTER_YOUR_ACCOUNT
import br.com.digital.store.strings.StringsUtils.FORGOT_PASS
import br.com.digital.store.strings.StringsUtils.OR
import br.com.digital.store.strings.StringsUtils.PASSWORD
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder
import lojavirtual.composeapp.generated.resources.Res
import lojavirtual.composeapp.generated.resources.mail

@Composable
fun CardSignIn() {
    Column(
        modifier = Modifier
            .onBorder(
                onClick = {},
                spaceSize = Themes.size.spaceSize36,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(color = Themes.colors.background)
            .padding(all = Themes.size.spaceSize36)
            .size(size = Themes.size.spaceSize400),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            var emailMutable by remember { mutableStateOf(value = "") }
            var passwordMutable by remember { mutableStateOf(value = "") }
            TextField(
                label = EMAIL,
                icon = Res.drawable.mail,
                value = emailMutable,
                onValueChange = { emailMutable = it }
            )
            TextPassword(
                label = PASSWORD,
                value = passwordMutable,
                onValueChange = { passwordMutable = it }
            )
            SimpleText(
                text = FORGOT_PASS,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            LoadingButton(
                label = ENTER_YOUR_ACCOUNT,
                onClick = {}
            )
            SimpleText(
                text = OR,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LoadingButton(
                label = CREATE_ONE_ACCOUNT,
                onClick = {}
            )
        }
    }
}
