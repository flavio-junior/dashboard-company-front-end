package br.com.digital.store.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.onClickable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconDefault(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    backgroundColor: Color = Themes.colors.background,
    tint: Color = Themes.colors.primary,
    contentDescription: String? = null,
    size: Dp = Themes.size.spaceSize24,
    onClick: () -> Unit = {}
) {
    Icon(
        painter = painterResource(resource = icon),
        contentDescription = contentDescription ?: EMPTY_TEXT,
        tint = tint,
        modifier = modifier
            .background(color = backgroundColor)
            .onClickable(onClick = onClick)
            .size(size = size)
    )
}