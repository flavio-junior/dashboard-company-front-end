package br.com.digital.store.features.payment.domain.converter

import br.com.digital.store.features.others.converterPageableDTOToVO
import br.com.digital.store.features.payment.data.dto.PaymentResponseDTO
import br.com.digital.store.features.payment.data.dto.PaymentsResponseDTO
import br.com.digital.store.features.payment.data.vo.PaymentResponseVO
import br.com.digital.store.features.payment.data.vo.PaymentsResponseVO

class ConverterPayment {

    fun converterContentDTOToVO(content: PaymentsResponseDTO): PaymentsResponseVO {
        return PaymentsResponseVO(
            totalPages = content.totalPages,
            content = converterPaymentsResponseDTOToVO(products = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    private fun converterPaymentsResponseDTOToVO(
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
