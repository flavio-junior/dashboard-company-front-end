package br.com.dashboard.company.vo.address

data class AddressResponseVO(
    var id: Long = 0,
    var street: String = "",
    var number: Int? = null,
    var district: String = "",
    var complement: String = ""
)