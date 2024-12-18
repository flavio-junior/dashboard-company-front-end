package br.com.dashboard.company.vo.order

import br.com.dashboard.company.utils.common.PaymentType

data class CloseOrderRequestVO(
    var type: PaymentType
)
