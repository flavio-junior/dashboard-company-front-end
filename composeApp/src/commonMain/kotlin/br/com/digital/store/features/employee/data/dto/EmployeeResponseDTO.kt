package br.com.digital.store.features.employee.data.dto

import br.com.digital.store.features.employee.domain.status.StatusEmployee
import br.com.digital.store.features.fee.domain.type.Function
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeResponseDTO(
    val id: Long = 0,
    @SerialName(value = "created_at")
    val createdAt: String = "",
    val name: String? = null,
    val function: Function? = null,
    val status: StatusEmployee? = null
)
