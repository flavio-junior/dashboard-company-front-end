package br.com.digital.store.features.report.data.dto

import br.com.digital.store.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ReportsResponseDTO(
    val totalPages: Int,
    val content: List<ReportResponseDTO>,
    val pageable: PageableDTO
)
