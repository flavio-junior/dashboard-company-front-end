package br.com.digital.store.features.report.data.repository

import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.report.data.dto.ReportRequestDTO
import br.com.digital.store.features.report.data.dto.ReportsResponseDTO
import br.com.digital.store.utils.NumbersUtils.NUMBER_SIXTY
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun findAllReports(
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ReportsResponseDTO>>
    fun createNewReport(report: ReportRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteReport(reportId: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
