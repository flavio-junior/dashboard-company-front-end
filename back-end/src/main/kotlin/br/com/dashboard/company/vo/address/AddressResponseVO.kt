package br.com.dashboard.company.vo.address

import br.com.dashboard.company.utils.common.AddressStatus

data class AddressResponseVO(
    var id: Long = 0,
    var status: AddressStatus? = null,
    var street: String = "",
    var number: Int? = null,
    var district: String = "",
    var complement: String = ""
)
