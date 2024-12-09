package br.com.dashboard.company.entities.payment

import br.com.dashboard.company.utils.common.PaymentType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tb_payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,
    @Enumerated(EnumType.STRING)
    var type: PaymentType? = null
)
