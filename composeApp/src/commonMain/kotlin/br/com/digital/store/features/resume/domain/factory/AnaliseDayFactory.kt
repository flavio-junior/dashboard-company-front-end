package br.com.digital.store.features.resume.domain.factory

import br.com.digital.store.components.strings.StringsUtils.ANALISE_DAY
import br.com.digital.store.components.strings.StringsUtils.ANALISE_MONTH
import br.com.digital.store.components.strings.StringsUtils.ANALISE_WEEK
import br.com.digital.store.components.strings.StringsUtils.ANALISE_YEAR
import br.com.digital.store.features.resume.domain.type.TypeAnalysis

fun analiseDayFactory(
    label: String
): TypeAnalysis {
    return when (label) {
        ANALISE_DAY -> TypeAnalysis.DAY
        ANALISE_WEEK -> TypeAnalysis.WEEK
        ANALISE_MONTH -> TypeAnalysis.MONTH
        ANALISE_YEAR -> TypeAnalysis.YEAR
        else -> TypeAnalysis.DAY
    }
}