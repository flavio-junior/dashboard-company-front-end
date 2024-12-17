package br.com.dashboard.company.vo.`object`

import br.com.dashboard.company.utils.common.Action
import br.com.dashboard.company.utils.common.ObjectStatus

data class UpdateObjectRequestVO(
    var action: Action,
    val status: ObjectStatus? = null,
    var quantity: Int = 0
)
