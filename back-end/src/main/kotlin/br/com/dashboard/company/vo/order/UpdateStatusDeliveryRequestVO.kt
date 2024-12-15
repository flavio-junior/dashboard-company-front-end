package br.com.dashboard.company.vo.order

import br.com.dashboard.company.utils.common.AddressStatus

data class UpdateStatusDeliveryRequestVO(
    var status: AddressStatus
)
