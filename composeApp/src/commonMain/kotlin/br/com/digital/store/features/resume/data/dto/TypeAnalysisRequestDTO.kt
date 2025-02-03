package br.com.digital.store.features.resume.data.dto

import br.com.digital.store.features.resume.domain.type.TypeAnalysis
import kotlinx.serialization.Serializable

@Serializable
data class TypeAnalysisRequestDTO(
    val type: TypeAnalysis
)
