package br.com.dashboard.company.vo.`object`

import br.com.dashboard.company.utils.common.TypeItem
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

data class ObjectRequestVO(
    var identifier: Long = 0,
    @Enumerated(EnumType.STRING)
    var type: TypeItem? = null,
    var quantity: Int = 0
)
