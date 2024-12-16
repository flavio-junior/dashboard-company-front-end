package br.com.dashboard.company.vo.order

import br.com.dashboard.company.utils.common.Status
import br.com.dashboard.company.utils.common.TypeOrder
import br.com.dashboard.company.vo.address.AddressResponseVO
import br.com.dashboard.company.vo.`object`.ObjectResponseVO
import br.com.dashboard.company.vo.payment.PaymentResponseVO
import br.com.dashboard.company.vo.reservation.ReservationResponseVO
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class OrderResponseVO(
    var id: Long = 0,
    @JsonProperty(value = "created_at")
    var createdAt: LocalDateTime? = null,
    var type: TypeOrder? = null,
    var status: Status? = null,
    var reservations: MutableList<ReservationResponseVO>? = null,
    var address: AddressResponseVO? = null,
    var objects: MutableList<ObjectResponseVO>? = null,
    var quantity: Int = 0,
    var total: Double = 0.0,
    var payment: PaymentResponseVO? = null
)
