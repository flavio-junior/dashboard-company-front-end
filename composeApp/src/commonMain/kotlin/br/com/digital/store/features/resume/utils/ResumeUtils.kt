package br.com.digital.store.features.resume.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Yellow
import br.com.digital.store.theme.Themes
import kotlin.math.PI

object ResumeUtils {
    const val RESUME_DAY = "Resumo do dia"
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

fun Float.toRadians(): Float = this * (PI / 180).toFloat()
