package br.com.digital.store.features.resume.domain

import br.com.digital.store.features.resume.data.dto.AnaliseDayDTO
import br.com.digital.store.features.resume.data.dto.DescriptionPaymentResponseDTO
import br.com.digital.store.features.resume.data.dto.TypePaymentDTO
import br.com.digital.store.features.resume.data.vo.AnaliseDayVO
import br.com.digital.store.features.resume.data.vo.DescriptionPaymentResponseVO
import br.com.digital.store.features.resume.data.vo.TypePaymentVO

class ConverterResume {

    fun converterAnaliseDayDTOToVO(content: AnaliseDayDTO): AnaliseDayVO {
        return AnaliseDayVO(
            content = convertContentAnaliseDayResponseDTOToVO(content = content.content),
            numberOrders = content.numberOrders,
            total = content.total,
            discount = content.discount
        )
    }

    private fun convertContentAnaliseDayResponseDTOToVO(
        content: List<TypePaymentDTO>?
    ):List<TypePaymentVO>? {
        return content?.map {
            TypePaymentVO(
                typeOrder = it.typeOrder,
                analise = converterDescriptionPaymentResponseDTOToVO(it.analise),
                numberOrders = it.numberOrders,
                total = it.total,
                discount = it.discount
            )
        }
    }

    private fun converterDescriptionPaymentResponseDTOToVO(
        analise: List<DescriptionPaymentResponseDTO>? = null
    ): List<DescriptionPaymentResponseVO>? {
        return analise?.map {
            DescriptionPaymentResponseVO(
                typeOrder = it.typeOrder,
                typePayment = it.typePayment,
                numberItems = it.numberItems,
                total = it.total,
                discount = it.discount
            )
        }
    }
}
