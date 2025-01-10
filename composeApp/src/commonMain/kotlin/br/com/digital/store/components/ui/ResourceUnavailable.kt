package br.com.digital.store.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.RESOURCE_UNAVAILABLE
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.power_off
import br.com.digital.store.theme.Themes

@Composable
fun ResourceUnavailable(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            icon = Res.drawable.power_off,
            size = Themes.size.spaceSize100
        )
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize16))
        Title(title = RESOURCE_UNAVAILABLE)
    }
}
