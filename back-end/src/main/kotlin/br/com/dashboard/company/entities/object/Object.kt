package br.com.dashboard.company.entities.`object`

import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.utils.common.ObjectStatus
import br.com.dashboard.company.utils.common.TypeItem
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
import jakarta.persistence.Table

@Entity
@Table(name = "tb_object")
data class Object(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var identifier: Long = 0,
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varying")
    var type: TypeItem? = null,
    var name: String = "",
    var price: Double = 0.0,
    var quantity: Int = 0,
    var total: Double = 0.0,
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varying")
    var status: ObjectStatus? = null,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_order_object",
        joinColumns = [JoinColumn(name = "fk_object", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_order", referencedColumnName = "id")]
    )
    var order: Order? = null
)
