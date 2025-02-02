package br.com.digital.store.features.fee.data.vo

import br.com.digital.store.features.fee.domain.type.Function

data class FeeResponseOrderVO(
    val id: Long? = 0,
    val percentage: Int? = 0,
    var assigned: Function? = null,
    var author: AuthorResponseVO? = null
)
