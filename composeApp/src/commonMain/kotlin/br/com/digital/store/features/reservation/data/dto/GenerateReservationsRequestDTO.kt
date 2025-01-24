package br.com.digital.store.features.reservation.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenerateReservationsRequestVO(
    val prefix: String = "",
    val start: Int = 0,
    val end: Int
)
