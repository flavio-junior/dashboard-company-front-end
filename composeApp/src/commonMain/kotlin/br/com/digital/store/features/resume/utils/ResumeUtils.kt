package br.com.digital.store.features.resume.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Yellow
import br.com.digital.store.components.strings.StringsUtils.ANALISE_DAY
import br.com.digital.store.components.strings.StringsUtils.ANALISE_MONTH
import br.com.digital.store.components.strings.StringsUtils.ANALISE_WEEK
import br.com.digital.store.components.strings.StringsUtils.ANALISE_YEAR
import br.com.digital.store.theme.Themes
import kotlin.math.PI

object ResumeUtils {
    const val RESUME_DAY = "Resumo do dia"
    const val TYPE_ORDERS = "Tipo do Pedido:"
    const val NUMBER_ORDERS = "Número de Pedidos:"
    const val RESUME_COMPLETED = "Resumo Completo:"
    const val NUMBER_DISCOUNT = "Número de Descontos:"
    const val EMPTY_LIST_RESUME = "Nenhum Resumo Encontrado!"
    const val NONE_RESUME = "Nenhum Resume para hoje!"
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
    ANALISE_DAY,
    ANALISE_WEEK,
    ANALISE_MONTH,
    ANALISE_YEAR
)

fun Float.toRadians(): Float = this * (PI / 180).toFloat()
