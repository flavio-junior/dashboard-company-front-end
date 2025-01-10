package br.com.digital.store.features.resume.utils

import kotlin.math.PI

object ResumeUtils {
    const val ANALISE_DAY = "Análise do dia:"
    const val TYPE_ORDERS = "Tipo do Pedido:"
    const val NUMBER_ORDERS = "Número de Pedidos:"
    const val RESUME_COMPLETED = "Resumo Completo:"
    const val NUMBER_DISCOUNT = "Número de Descontos:"
}

fun Float.toRadians(): Float = this * (PI / 180).toFloat()
