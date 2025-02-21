package br.com.digital.store.features.resume.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Yellow
import br.com.digital.store.components.strings.StringsUtils.DAY
import br.com.digital.store.components.strings.StringsUtils.DAYS
import br.com.digital.store.components.strings.StringsUtils.MONTH
import br.com.digital.store.components.strings.StringsUtils.WEEK
import br.com.digital.store.components.strings.StringsUtils.YEAR
import br.com.digital.store.theme.Themes
import kotlin.math.PI

object ResumeUtils {
    const val TYPE_ORDERS = "Tipo do Pedido:"
    const val NUMBER_ORDERS = "Número de Pedidos:"
    const val RESUME_COMPLETED = "Resumo Completo:"
    const val NUMBER_DISCOUNT = "Número de Descontos:"
}

@Composable
fun ColorsResume() = listOf(
    Blue,
    Themes.colors.primary,
    Themes.colors.error,
    Themes.colors.secondary,
    Themes.colors.secondary,
    Yellow
)

val listAnalise = listOf(
    DAY,
    WEEK,
    MONTH,
    YEAR
)

val selectTypeDate = listOf(
    DAY,
    DAYS
)

fun Float.toRadians(): Float = this * (PI / 180).toFloat()
