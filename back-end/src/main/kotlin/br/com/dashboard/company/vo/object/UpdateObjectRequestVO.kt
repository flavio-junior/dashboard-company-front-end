package br.com.dashboard.company.vo.`object`

import br.com.dashboard.company.utils.common.Action

data class UpdateObjectRequestVO(
    var action: Action,
    var quantity: Int = 0
)
