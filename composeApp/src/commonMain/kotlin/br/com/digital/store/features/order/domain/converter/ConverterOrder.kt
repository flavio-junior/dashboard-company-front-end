package br.com.digital.store.features.order.domain.converter

import br.com.digital.store.features.fee.data.dto.AuthorResponseDTO
import br.com.digital.store.features.fee.data.dto.FeeResponseOrderDTO
import br.com.digital.store.features.fee.data.vo.AuthorResponseVO
import br.com.digital.store.features.fee.data.vo.FeeResponseOrderVO
import br.com.digital.store.features.order.data.dto.AddressResponseDTO
import br.com.digital.store.features.order.data.dto.ObjectResponseDTO
import br.com.digital.store.features.order.data.dto.OrderResponseDTO
import br.com.digital.store.features.order.data.dto.OrdersResponseDTO
import br.com.digital.store.features.order.data.dto.OverviewResponseDTO
import br.com.digital.store.features.order.data.dto.PaymentResponseDTO
import br.com.digital.store.features.order.data.vo.AddressResponseVO
import br.com.digital.store.features.order.data.vo.ObjectResponseVO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.data.vo.OrdersResponseVO
import br.com.digital.store.features.order.data.vo.OverviewResponseVO
import br.com.digital.store.features.order.data.vo.PaymentResponseVO
import br.com.digital.store.features.others.converterPageableDTOToVO
import br.com.digital.store.features.reservation.data.dto.ReservationResponseDTO
import br.com.digital.store.features.reservation.data.vo.ReservationResponseVO

class ConverterOrder {

    fun converterContentDTOToVO(content: OrdersResponseDTO): OrdersResponseVO {
        return OrdersResponseVO(
            totalPages = content.totalPages,
            content = converterOrdersResponseDTOToVO(orders = content.content),
            pageable = converterPageableDTOToVO(pageable = content.pageable)
        )
    }

    fun converterOrderResponseDTOToVO(order: OrderResponseDTO): OrderResponseVO {
        return OrderResponseVO(
            id = order.id,
            createdAt = order.createdAt,
            qrCode = order.qrCode,
            type = order.type,
            status = order.status,
            reservations = converterReservationsResponseDTOToVO(reservations = order.reservations),
            fee = converterFeeResponseDTOToVO(fee = order.fee),
            address = converterAddressResponseDTOToVO(address = order.address),
            objects = converterObjectResponseDTOToVO(objects = order.objects),
            quantity = order.quantity,
            total = order.total,
            payment = converterPaymentResponseDTOToVO(payment = order.payment)
        )
    }

    private fun converterOrdersResponseDTOToVO(
        orders: List<OrderResponseDTO>
    ): List<OrderResponseVO> {
        return orders.map {
            OrderResponseVO(
                id = it.id,
                createdAt = it.createdAt,
                qrCode = it.qrCode,
                type = it.type,
                status = it.status,
                reservations = converterReservationsResponseDTOToVO(reservations = it.reservations),
                fee = converterFeeResponseDTOToVO(fee = it.fee),
                address = converterAddressResponseDTOToVO(address = it.address),
                objects = converterObjectResponseDTOToVO(objects = it.objects),
                quantity = it.quantity,
                total = it.total,
                payment = converterPaymentResponseDTOToVO(payment = it.payment)
            )
        }
    }

    private fun converterReservationsResponseDTOToVO(
        reservations: List<ReservationResponseDTO>? = null
    ): List<ReservationResponseVO>? {
        return reservations?.map {
            ReservationResponseVO(
                id = it.id,
                name = it.name
            )
        }
    }

    private fun converterFeeResponseDTOToVO(
        fee: FeeResponseOrderDTO? = null
    ): FeeResponseOrderVO {
        return FeeResponseOrderVO(
            id = fee?.id,
            percentage = fee?.percentage,
            assigned = fee?.assigned,
            author = converterAuthorResponseDTOToVO(fee = fee?.author)
        )
    }

    private fun converterAuthorResponseDTOToVO(
        fee: AuthorResponseDTO? = null
    ): AuthorResponseVO {
        return AuthorResponseVO(
            id = fee?.id ?: 0,
            author = fee?.author ?: "",
            assigned = fee?.assigned ?: ""
        )
    }

    private fun converterAddressResponseDTOToVO(
        address: AddressResponseDTO? = null
    ): AddressResponseVO {
        return AddressResponseVO(
            id = address?.id,
            status = address?.status,
            complement = address?.complement,
            district = address?.district,
            number = address?.number,
            street = address?.street
        )
    }

    private fun converterObjectResponseDTOToVO(
        objects: List<ObjectResponseDTO>? = null
    ): List<ObjectResponseVO>? {
        return objects?.map {
            ObjectResponseVO(
                id = it.id,
                identifier = it.identifier,
                name = it.name,
                price = it.price,
                quantity = it.quantity,
                total = it.total,
                type = it.type,
                status = it.status,
                overview = converterOverviewResponseDTOToVO(objects = it.overview)
            )
        }
    }

    private fun converterOverviewResponseDTOToVO(
        objects: List<OverviewResponseDTO>? = null
    ): List<OverviewResponseVO> {
        return objects?.map {
            OverviewResponseVO(
                id = it.id,
                createdAt = it.createdAt,
                status = it.status,
                quantity = it.quantity
            )
        } ?: emptyList()
    }

    private fun converterPaymentResponseDTOToVO(
        payment: PaymentResponseDTO? = null
    ): PaymentResponseVO {
        return PaymentResponseVO(
            id = payment?.id ?: 0,
            date = payment?.date,
            hour = payment?.hour,
            code = payment?.code,
            author = payment?.author,
            assigned = payment?.assigned,
            typeOrder = payment?.typeOrder,
            typePayment = payment?.typePayment,
            discount = payment?.discount,
            valueDiscount = payment?.valueDiscount,
            fee = payment?.fee,
            valueFee = payment?.valueFee,
            total = payment?.total ?: 0.0
        )
    }
}
