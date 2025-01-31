package br.com.digital.store.features.employee.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeNameRequestDTO(
    val name: String
)
