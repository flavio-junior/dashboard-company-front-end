package br.com.digital.store.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.print
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder

@Composable
fun PrintDocument() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .onBorder(
                onClick = {},
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .size(size = Themes.size.spaceSize64)
            .padding(all = Themes.size.spaceSize8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconDefault(icon = Res.drawable.print, size = Themes.size.spaceSize48)
    }
}
