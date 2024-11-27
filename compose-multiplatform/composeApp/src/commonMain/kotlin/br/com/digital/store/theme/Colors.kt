package br.com.digital.store.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val DarkColor = Colors(
    primary = Color(color = 0xFFf2f2f2),
    secondary = Color(color = 0xFFe8702a),
    background = Color(color = 0xFF000000),
    success = Color(color = 0xFF000000),
    error = Color(color = 0xFFFF0000)
)

val LightColor = Colors(
    primary = Color(color = 0xFF000000),
    secondary = Color(color = 0xFFe8702a),
    background = Color(color = 0xFFf2f2f2),
    success = Color(color = 0xFFBBE580),
    error = Color(color = 0xFFFF0000)
)

object CommonColors {
    val ITEM_SELECTED = Color(color = 0xFF17202a)
}

class Colors(
    primary: Color,
    secondary: Color,
    background: Color,
    success: Color,
    error: Color,
) {
    var primary by mutableStateOf(primary)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var success by mutableStateOf(success)
        private set

    var error by mutableStateOf(error)
        private set

    var background by mutableStateOf(background)
        private set

    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        background: Color = this.background,
        success: Color = this.success,
        error: Color = this.error
    ) = Colors(
        primary = primary,
        secondary = secondary,
        background = background,
        success = success,
        error = error
    )

    fun updateColorsFrom(other: Colors) {
        primary = other.primary
        secondary = other.secondary
        success = other.success
        background = other.background
        error = other.error
    }
}

val LocalColors = staticCompositionLocalOf { LightColor }
