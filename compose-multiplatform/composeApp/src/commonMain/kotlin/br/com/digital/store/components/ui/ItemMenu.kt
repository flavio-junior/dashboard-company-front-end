package br.com.digital.store.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.onBorder
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun ItemMenu(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .onBorder(
                onClick = {},
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(color = Themes.colors.background)
            .size(size = Themes.size.spaceSize200)
            .padding(all = Themes.size.spaceSize16)
        ,
        verticalArrangement = Arrangement.Center
    ) {
        IconDefault(
            icon = icon,
            contentDescription = label
        )
        Title(title = label, textAlign = TextAlign.Center)
    }
}