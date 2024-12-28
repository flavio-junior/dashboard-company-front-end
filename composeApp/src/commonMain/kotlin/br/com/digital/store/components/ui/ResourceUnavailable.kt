package br.com.digital.store.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import br.com.digital.store.components.strings.StringsUtils.RESOURCE_UNAVAILABLE
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.power_off
import br.com.digital.store.theme.Themes

@Composable
fun ResourceUnavailable() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            icon = Res.drawable.power_off,
            size = Themes.size.spaceSize100
        )
        Title(title = RESOURCE_UNAVAILABLE)
    }
}
