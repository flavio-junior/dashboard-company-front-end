package br.com.dashboard.company.entities.reservation

import jakarta.persistence.*

@Entity
@Table(name = "tb_order")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "name", nullable = false, unique = true)
    var name: String = ""
)
