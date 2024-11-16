package br.com.dashboard.company.entities.order

import br.com.dashboard.company.entities.address.Address
import br.com.dashboard.company.entities.items.Items
import br.com.dashboard.company.entities.payment.Payment
import br.com.dashboard.company.entities.reservation.Reservation
import br.com.dashboard.company.utils.TypeOrder
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "tb_order")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false)
    var createdAt: Instant? = null,
    var type: TypeOrder? = null,
    var reservation: Reservation? = null,
    var address: Address? = null,
    var items: Items? = null,
    var total: Int = 0,
    var payment: Payment? = null
)
