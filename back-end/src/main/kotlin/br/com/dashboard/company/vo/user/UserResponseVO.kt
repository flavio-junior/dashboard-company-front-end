package br.com.dashboard.company.vo.user

import br.com.dashboard.company.utils.common.TypeAccount

data class UserResponseVO(
    var id: Long = 0,
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var type: TypeAccount? = null
)
