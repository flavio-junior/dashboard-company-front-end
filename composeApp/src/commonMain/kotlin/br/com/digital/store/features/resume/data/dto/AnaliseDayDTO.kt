package br.com.digital.store.features.resume.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnaliseDayDTO(
    val content: List<TypePaymentDTO>?,
    @SerialName("number_orders")
    val numberOrders: Long,
    val total: Double,
    val discount: Long
)
