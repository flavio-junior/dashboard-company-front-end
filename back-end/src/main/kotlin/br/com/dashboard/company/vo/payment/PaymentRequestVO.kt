package br.com.dashboard.company.vo.payment

import br.com.dashboard.company.utils.common.PaymentType

data class PaymentRequestVO(
    var type: PaymentType? = null
)
