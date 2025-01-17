package br.com.digital.store.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.R
import br.com.digital.store.components.ui.Description
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onClickable

enum class Position {
    START,
    END
}

@Composable
fun ActionButton(
    label: String? = null,
    position: Position = Position.START,
    icon: Int? = null,
    backgroundColor: Color = Themes.colors.background,
    textColor: Color = Themes.colors.primary,
    goToBack: () -> Unit = { },
    title: Int
) {
    if (position == Position.START) {
        PositionStartComponents(
            label = label,
            icon = icon ?: R.drawable.back,
            goToBack = goToBack,
            backgroundColor = backgroundColor,
            textColor = textColor,
            title = title
        )
    } else {
        PositionEndComponents(
            label = title,
            icon = icon ?: R.drawable.next,
            contentDescription = label,
            backgroundColor = backgroundColor,
            textColor = textColor,
            goToBack = goToBack
        )
    }
}

@Composable
private fun PositionStartComponents(
    label: String? = null,
    icon: Int,
    goToBack: () -> Unit = { },
    backgroundColor: Color,
    textColor: Color,
    title: Int
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .background(color = backgroundColor)
            .fillMaxWidth()
            .height(height = Themes.size.spaceSize64)
            .padding(horizontal = Themes.size.spaceSize36),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.onClickable(goToBack),
            tint = textColor
        )
        Description(
            description = stringResource(id = title),
            color = textColor,
            modifier = Modifier
                .weight(WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PositionEndComponents(
    label: Int,
    icon: Int,
    contentDescription: String? = null,
    backgroundColor: Color,
    textColor: Color,
    goToBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = backgroundColor)
            .onClickable(goToBack)
            .height(height = Themes.size.spaceSize64)
    ) {
        Description(
            description = stringResource(id = label),
            color = textColor,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
        )
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            tint = textColor
        )
    }
}
