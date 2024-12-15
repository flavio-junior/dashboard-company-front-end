package br.com.dashboard.company.vo.address

import br.com.dashboard.company.utils.common.AddressStatus

data class AddressRequestVO(
    var status: AddressStatus = AddressStatus.PENDING_DELIVERY,
    var street: String = "",
    var number: Int? = null,
    var district: String = "",
    var complement: String = ""
)
