package br.com.dashboard.company.entities.payment

import br.com.dashboard.company.utils.PaymentStatus
import br.com.dashboard.company.utils.PaymentType
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "tb_payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false)
    var createdAt: Instant? = null,
    @Enumerated(EnumType.STRING)
    var status: PaymentStatus? = null,
    @Enumerated(EnumType.STRING)
    var type: PaymentType? = null
)
