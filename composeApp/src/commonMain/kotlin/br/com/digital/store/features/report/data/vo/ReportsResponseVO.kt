package br.com.digital.store.features.report.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class ReportsResponseVO(
    val totalPages: Int,
    val content: List<ReportResponseVO>,
    val pageable: PageableVO
)
