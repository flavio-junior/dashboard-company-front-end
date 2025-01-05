package br.com.digital.store.features.report.domain

import br.com.digital.store.features.others.converterPageableDTOToVO
import br.com.digital.store.features.report.data.dto.ReportResponseDTO
import br.com.digital.store.features.report.data.dto.ReportsResponseDTO
import br.com.digital.store.features.report.data.vo.ReportResponseVO
import br.com.digital.store.features.report.data.vo.ReportsResponseVO

class ConverterReport {

    fun converterContentDTOToVO(content: ReportsResponseDTO): ReportsResponseVO {
        return ReportsResponseVO(
            totalPages = content.totalPages,
            content = converterReportsResponseDTOToVO(products = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterReportsResponseDTOToVO(
        products: List<ReportResponseDTO>
    ): List<ReportResponseVO> {
        return products.map {
            ReportResponseVO(
                id = it.id,
                date = it.date,
                hour = it.hour,
                resume = it.resume,
                author = it.author
            )
        }
    }
}
