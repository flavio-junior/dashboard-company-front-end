package br.com.digital.store.features.employee.data.vo

import br.com.digital.store.features.employee.domain.status.StatusEmployee
import br.com.digital.store.features.fee.domain.type.Function

data class EmployeeResponseVO(
    val id: Long,
    val createdAt: String,
    val name: String,
    val function: Function,
    val status: StatusEmployee
)
