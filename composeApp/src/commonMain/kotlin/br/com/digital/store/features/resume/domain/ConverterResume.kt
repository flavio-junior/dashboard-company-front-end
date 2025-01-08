package br.com.digital.store.features.resume.domain

import br.com.digital.store.features.resume.data.dto.AnalisePaymentResponseDTO
import br.com.digital.store.features.resume.data.dto.PaymentSummaryResumeResponseDTO
import br.com.digital.store.features.resume.data.vo.AnalisePaymentResponseVO
import br.com.digital.store.features.resume.data.vo.PaymentSummaryResponseVO

class ConverterResume {

    fun converterContentDTOToVO(content: AnalisePaymentResponseDTO): AnalisePaymentResponseVO {
        return AnalisePaymentResponseVO(
            analise = converterPaymentSummaryResponseDTOToVO(analise = content.analise),
            total = content.total,
            discount = content.discount
        )
    }

    private fun converterPaymentSummaryResponseDTOToVO(
        analise: List<PaymentSummaryResumeResponseDTO>?
    ): List<PaymentSummaryResponseVO>? {
        return analise?.map {
            PaymentSummaryResponseVO(
                typeOrder = it.typeOrder,
                typePayment = it.typePayment,
                count = it.count,
                total = it.total
            )
        }
    }
}
