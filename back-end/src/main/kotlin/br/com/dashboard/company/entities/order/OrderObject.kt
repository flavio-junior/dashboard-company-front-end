package br.com.dashboard.company.entities.order

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Embeddable
data class OrderObject(
    @Column(name = "fk_order")
    var order: Long? = 0,
    @Column(name = "fk_object")
    var `object`: Long? = 0
)

@Entity
@Table(name = "tb_order_object")
data class JoinOrderAndObject(
    @EmbeddedId
    var keys: OrderObject? = null
)
