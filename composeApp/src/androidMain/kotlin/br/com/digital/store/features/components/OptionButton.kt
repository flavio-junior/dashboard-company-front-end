package br.com.digital.store.features.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import br.com.digital.store.components.ui.SimpleText
import br.com.digital.store.theme.Themes
import br.com.digital.store.theme.TypeFont
import br.com.digital.store.utils.onBorder

@Composable
fun OptionButton(
    icon: Int,
    label: String,
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = Modifier
            .onBorder(
                onClick = onClick,
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2
            )
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Themes.colors.primary
        )
        SimpleText(text = label, typeFont = TypeFont.ONEST_BOLD)
    }
}
