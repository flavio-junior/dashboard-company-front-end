package br.com.dashboard.company.vo.order

import br.com.dashboard.company.utils.common.PaymentStatus
import br.com.dashboard.company.utils.common.PaymentType

data class CloseOrderRequestVO(
    var id: Long,
    var status: PaymentStatus,
    var type: PaymentType
)
