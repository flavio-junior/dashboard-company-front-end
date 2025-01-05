package br.com.digital.store.features.report.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReportResponseDTO(
    var id: Long = 0,
    var date: String? = null,
    var hour: String? = null,
    var resume: String? = null,
    var author: String? = null
)
