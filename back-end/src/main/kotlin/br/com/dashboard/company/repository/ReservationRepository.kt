package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.reservation.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {

    @Query("SELECT c FROM Reservation c WHERE c.name = :name")
    fun checkNameReservationAlreadyExists(@Param("name") name: String): Reservation?
}
