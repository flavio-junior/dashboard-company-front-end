package br.com.digital.store.features.resume.domain.factory

import br.com.digital.store.components.strings.StringsUtils.DAY
import br.com.digital.store.components.strings.StringsUtils.MONTH
import br.com.digital.store.components.strings.StringsUtils.WEEK
import br.com.digital.store.components.strings.StringsUtils.YEAR
import br.com.digital.store.features.resume.domain.type.TypeAnalysis

fun analiseDayFactory(
    label: String
): TypeAnalysis {
    return when (label) {
        DAY -> TypeAnalysis.DAY
        WEEK -> TypeAnalysis.WEEK
        MONTH -> TypeAnalysis.MONTH
        YEAR -> TypeAnalysis.YEAR
        else -> TypeAnalysis.DAY
    }
}