package br.com.dashboard.company.service

import br.com.dashboard.company.entities.order.JoinOrderAndReservation
import br.com.dashboard.company.entities.order.OrderReservation
import br.com.dashboard.company.entities.reservation.Reservation
import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.OrderReservationRepository
import br.com.dashboard.company.repository.ReservationRepository
import br.com.dashboard.company.utils.others.ConverterUtils.parseListObjects
import br.com.dashboard.company.utils.others.ConverterUtils.parseObject
import br.com.dashboard.company.vo.reservation.ReservationRequestVO
import br.com.dashboard.company.vo.reservation.ReservationResponseVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService {

    @Autowired
    private lateinit var reservationRepository: ReservationRepository

    @Autowired
    private lateinit var orderReservationRepository: OrderReservationRepository

    @Transactional(readOnly = true)
    fun findAllReservations(): List<ReservationResponseVO> {
        val reservations = reservationRepository.findAll()
        return reservations.map { reservation -> parseObject(reservation, ReservationResponseVO::class.java) }
    }

    @Transactional(readOnly = true)
    fun findReservationById(
        id: Long
    ): ReservationResponseVO {
        val reservation = getReservation(id = id)
        return parseObject(reservation, ReservationResponseVO::class.java)
    }

    private fun getReservation(id: Long): Reservation {
        return reservationRepository.findById(id)
            .orElseThrow { ResourceNotFoundException(RESERVATION_NOT_FOUND) }
    }

    @Transactional
    fun createNewReservation(
        reservation: ReservationRequestVO
    ): ReservationResponseVO {
        if (!checkNameReservationAlreadyExists(name = reservation.name)) {
            val reservationResult: Reservation = parseObject(reservation, Reservation::class.java)
            return parseObject(reservationRepository.save(reservationResult), ReservationResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_RESERVATION)
        }
    }

    private fun checkNameReservationAlreadyExists(
        name: String
    ): Boolean {
        val reservationResult = reservationRepository.checkNameReservationAlreadyExists(name = name)
        return reservationResult != null
    }

    @Transactional
    fun converterReservations(
        id: Long,
        reservations: MutableList<ReservationResponseVO>? = null
    ): MutableList<Reservation> {
        var result: MutableList<Reservation> = emptyList<Reservation>().toMutableList()
        reservations?.let {
            result = parseListObjects(it, Reservation::class.java)
            result.forEach { item ->
                orderReservationRepository.save(
                    JoinOrderAndReservation(
                        keys = OrderReservation(
                            order = id,
                            reservation = item.id
                        )
                    )
                )
            }
        }
        return result
    }

    @Transactional
    fun updateReservation(
        reservation: ReservationResponseVO
    ): ReservationResponseVO {
        if (!checkNameReservationAlreadyExists(name = reservation.name)) {
            val reservationSaved: Reservation = getReservation(id = reservation.id)
            reservationSaved.name = reservation.name
            return parseObject(reservationRepository.save(reservationSaved), ReservationResponseVO::class.java)
        } else {
            throw DuplicateNameException(message = DUPLICATE_NAME_RESERVATION)
        }
    }

    fun deleteReservation(id: Long) {
        reservationRepository.delete(getReservation(id = id))
    }

    companion object {
        const val RESERVATION_NOT_FOUND = "Reservation not found!"
        const val DUPLICATE_NAME_RESERVATION = "The Reservation already exists"
    }
}
