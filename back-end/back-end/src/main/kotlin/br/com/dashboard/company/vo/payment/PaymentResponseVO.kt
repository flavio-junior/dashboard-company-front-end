package br.com.dashboard.company.vo.payment

import br.com.dashboard.company.utils.common.PaymentType
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class PaymentResponseVO(
    var id: Long = 0,
    @JsonProperty(value = "created_at")
    var createdAt: LocalDateTime? = null,
    var type: PaymentType? = null,
    var total: Double = 0.0
)
