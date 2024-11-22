package br.com.digital.store.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import lojavirtual.composeapp.generated.resources.Res
import lojavirtual.composeapp.generated.resources.onest_bold
import lojavirtual.composeapp.generated.resources.onest_medium
import lojavirtual.composeapp.generated.resources.onest_regular
import org.jetbrains.compose.resources.Font

private val fontSize = FontSize

data class Typography(
    val color: Color = Color.Black,
    val textAlign: TextAlign = TextAlign.Start,
    val typeFont: TypeFont? = null
) {

    @Composable
    fun title() = TextStyle(
        color = color,
        fontSize = fontSize.fontSize32,
        fontWeight = FontWeight.SemiBold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun subTitle() = TextStyle(
        color = color,
        fontSize = fontSize.fontSize24,
        fontWeight = FontWeight.Medium,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_MEDIUM),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun description() = TextStyle(
        color = color,
        fontSize = fontSize.fontSize20,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_REGULAR),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun simpleText(): TextStyle = TextStyle(
        color = color,
        fontSize = fontSize.fontSize16,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_REGULAR),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun infoText() = TextStyle(
        color = color,
        fontSize = fontSize.fontSize14,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun smallText() = TextStyle(
        color = color,
        fontSize = fontSize.fontSize12,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )

    @Composable
    fun miniText() = TextStyle(
        color = color,
        fontSize = fontSize.fontSize8,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        lineHeight = TextUnit.Unspecified,
        fontFamily = SelectFontFamily(typeFont = typeFont ?: TypeFont.ONEST_BOLD),
        textDecoration = TextDecoration.None,
        fontStyle = FontStyle.Normal,
        letterSpacing = TextUnit.Unspecified
    )
}

val LocalTypography = staticCompositionLocalOf {
    Typography()
}

@Composable
private fun SelectFontFamily(typeFont: TypeFont): FontFamily {
    return when (typeFont) {
        TypeFont.ONEST_BOLD -> FontFamily(Font(Res.font.onest_bold))
        TypeFont.ONEST_MEDIUM -> FontFamily(Font(Res.font.onest_medium))
        TypeFont.ONEST_REGULAR -> FontFamily(Font(Res.font.onest_regular))
    }
}
