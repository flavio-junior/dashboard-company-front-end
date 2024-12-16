package br.com.dashboard.company.entities.order

import br.com.dashboard.company.entities.address.Address
import br.com.dashboard.company.entities.`object`.Object
import br.com.dashboard.company.entities.payment.Payment
import br.com.dashboard.company.entities.reservation.Reservation
import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.utils.common.Status
import br.com.dashboard.company.utils.common.TypeOrder
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tb_order")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @Enumerated(EnumType.STRING)
    var type: TypeOrder? = null,
    @Enumerated(EnumType.STRING)
    var status: Status? = null,
    @OneToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "tb_order_reservation",
        joinColumns = [JoinColumn(name = "fk_order", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_reservation", referencedColumnName = "id")]
    )
    var reservations: MutableList<Reservation>? = null,
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinTable(
        name = "tb_order_address",
        joinColumns = [JoinColumn(name = "fk_order", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_address", referencedColumnName = "id")]
    )
    var address: Address? = null,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinTable(
        name = "tb_order_object",
        joinColumns = [JoinColumn(name = "fk_order", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_object", referencedColumnName = "id")]
    )
    var objects: MutableList<Object>? = null,
    var quantity: Int = 0,
    var total: Double = 0.0,
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinTable(
        name = "tb_order_payment",
        joinColumns = [JoinColumn(name = "fk_order", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_payment", referencedColumnName = "id")]
    )
    var payment: Payment? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_user_order",
        joinColumns = [JoinColumn(name = "fk_order", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_user", referencedColumnName = "id")]
    )
    var user: User? = null
)
