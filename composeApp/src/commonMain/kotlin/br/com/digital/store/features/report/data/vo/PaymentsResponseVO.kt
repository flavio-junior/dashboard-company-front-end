package br.com.digital.store.features.report.data.vo

import br.com.digital.store.features.others.vo.PageableVO

data class PaymentsResponseVO(
    val totalPages: Int,
    val content: List<PaymentResponseVO>,
    val pageable: PageableVO
)
