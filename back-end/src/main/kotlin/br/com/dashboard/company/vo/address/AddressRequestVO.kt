package br.com.dashboard.company.vo.address

data class AddressRequestVO(
    var street: String = "",
    var number: Int? = null,
    var district: String = "",
    var complement: String = ""
)
