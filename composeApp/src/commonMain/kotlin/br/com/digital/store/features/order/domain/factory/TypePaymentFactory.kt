package br.com.digital.store.features.order.domain.factory

import br.com.digital.store.features.order.data.dto.PaymentRequestDTO
import br.com.digital.store.features.order.domain.type.PaymentType
import br.com.digital.store.features.order.utils.OrderUtils.CREDIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.DEBIT_CAD
import br.com.digital.store.features.order.utils.OrderUtils.MONEY
import br.com.digital.store.features.order.utils.OrderUtils.PIX

fun typePaymentFactory(
    payment: String,
    discount: Boolean? = null,
    value: Double? = 0.0,
    remove: Boolean = false
): PaymentRequestDTO {
    val response: Pair<Boolean, Double?> = if (discount == true) {
        Pair(first = true, second = value)
    } else {
        Pair(first = false, second = null)
    }
    return when (payment) {
        CREDIT_CAD -> {
            PaymentRequestDTO(
                type = PaymentType.CREDIT_CAD,
                discount = response.first,
                value = response.second,
                remove = remove
            )
        }

        DEBIT_CAD -> {
            PaymentRequestDTO(
                type = PaymentType.DEBIT_CAD,
                discount = response.first,
                value = response.second,
                remove = remove
            )
        }

        MONEY -> {
            PaymentRequestDTO(
                type = PaymentType.MONEY,
                discount = response.first,
                value = response.second,
                remove = remove
            )
        }

        PIX -> {
            PaymentRequestDTO(
                type = PaymentType.PIX,
                discount = response.first,
                value = response.second,
                remove = remove
            )
        }

        else -> {
            PaymentRequestDTO()
        }
    }
}
