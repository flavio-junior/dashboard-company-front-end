package br.com.dashboard.company.vo.order

import br.com.dashboard.company.utils.common.TypeOrder
import br.com.dashboard.company.vo.address.AddressRequestVO
import br.com.dashboard.company.vo.`object`.ObjectRequestVO
import br.com.dashboard.company.vo.reservation.ReservationResponseVO

data class OrderRequestVO(
    var type: TypeOrder? = null,
    var reservations: MutableList<ReservationResponseVO>? = null,
    var address: AddressRequestVO? = null,
    var objects: MutableList<ObjectRequestVO>? = null
)
