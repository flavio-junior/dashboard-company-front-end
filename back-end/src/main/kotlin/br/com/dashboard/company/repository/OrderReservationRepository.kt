package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.order.JoinOrderAndReservation
import org.springframework.data.jpa.repository.JpaRepository

interface OrderReservationRepository : JpaRepository<JoinOrderAndReservation, Long>
