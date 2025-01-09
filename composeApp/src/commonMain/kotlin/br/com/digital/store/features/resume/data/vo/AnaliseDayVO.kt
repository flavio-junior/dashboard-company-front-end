package br.com.digital.store.features.resume.data.vo

data class AnaliseDayVO(
    val content: List<TypePaymentVO>?,
    val numberOrders: Long,
    val total: Double,
    val discount: Long
)
