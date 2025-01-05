package br.com.digital.store.features.report.domain.converter

import br.com.digital.store.features.others.converterPageableDTOToVO
import br.com.digital.store.features.report.data.dto.PaymentResponseDTO
import br.com.digital.store.features.report.data.dto.PaymentsResponseDTO
import br.com.digital.store.features.report.data.vo.PaymentResponseVO
import br.com.digital.store.features.report.data.vo.PaymentsResponseVO

class ConverterReport {

    fun converterContentDTOToVO(content: PaymentsResponseDTO): PaymentsResponseVO {
        return PaymentsResponseVO(
            totalPages = content.totalPages,
            content = converterCheckoutsResponseDTOToVO(products = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterCheckoutsResponseDTOToVO(
        products: List<PaymentResponseDTO>
    ): List<PaymentResponseVO> {
        return products.map {
            PaymentResponseVO(
                id = it.id,
                date = it.date,
                hour = it.hour,
                code = it.code,
                typeOrder = it.typeOrder,
                typePayment = it.typePayment,
                discount = it.discount,
                valueDiscount = it.valueDiscount,
                total = it.total
            )
        }
    }
}
