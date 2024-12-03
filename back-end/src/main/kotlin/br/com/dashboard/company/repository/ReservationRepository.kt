package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.reservation.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {

    @Query(value = "SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.name = :name")
    fun checkNameReservationAlreadyExists(
        @Param("userId") userId: Long,
        @Param("name") name: String
    ): Reservation?

    @Query(
        value = """
        SELECT r FROM Reservation r
            WHERE r.user.id = :userId
        AND (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """
    )
    fun findAllReservations(
        @Param("userId") userId: Long,
        @Param("name") name: String?, pageable: Pageable
    ): Page<Reservation>?

    @Query(value = "SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.id = :idReservation")
    fun findReservationById(
        @Param("userId") userId: Long,
        @Param("idReservation") reservationId: Long
    ): Reservation?

    @Modifying
    @Query(value = "DELETE FROM Reservation r WHERE r.id = :reservationId AND r.user.id = :userId")
    fun deleteReservationById(
        @Param("userId") userId: Long,
        @Param("reservationId") reservationId: Long
    ): Int
}
