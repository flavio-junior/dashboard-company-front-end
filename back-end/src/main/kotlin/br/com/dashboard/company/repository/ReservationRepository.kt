package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.reservation.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.name = :name")
    fun checkNameReservationAlreadyExists(@Param("name") name: String): Reservation?

    @Query(
        """
        SELECT r FROM Reservation r
            WHERE :name IS NULL OR LOWER(CAST(r.name AS string)) LIKE LOWER(CONCAT('%', :name, '%'))
    """
    )
    fun findAllReservations(@Param("name") name: String?, pageable: Pageable): Page<Reservation>?
}
