package br.com.dashboard.company.vo.payment

import br.com.dashboard.company.utils.common.PaymentStatus
import br.com.dashboard.company.utils.common.PaymentType

data class PaymentRequestVO(
    var status: PaymentStatus? = null,
    var type: PaymentType? = null
)
