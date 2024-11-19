package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.payment.Payment
import br.com.dashboard.company.utils.common.PaymentStatus
import br.com.dashboard.company.utils.common.PaymentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<Payment, Long> {

    @Modifying
    @Query("UPDATE Payment p SET p.status =:status, p.type =:type WHERE p.id =:id")
    fun updatePayment(
        @Param("id") id: Long,
        @Param("status") status: PaymentStatus,
        @Param("type") type: PaymentType
    )
}
