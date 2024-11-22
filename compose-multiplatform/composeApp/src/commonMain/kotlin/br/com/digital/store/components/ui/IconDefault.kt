package br.com.digital.store.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.onClickable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconDefault(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {
    Icon(
        painter = painterResource(resource = icon),
        contentDescription = contentDescription ?: EMPTY_TEXT,
        tint = Themes.colors.primary,
        modifier = modifier
            .background(color = Themes.colors.background)
            .onClickable(onClick = onClick)
            .width(width = Themes.size.spaceSize48)
    )
}