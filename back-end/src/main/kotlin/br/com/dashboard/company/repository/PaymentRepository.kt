package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.payment.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : JpaRepository<Payment, Long>
