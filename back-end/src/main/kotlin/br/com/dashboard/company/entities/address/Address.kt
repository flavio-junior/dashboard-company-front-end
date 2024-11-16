package br.com.dashboard.company.entities.address

import jakarta.persistence.*

@Entity
@Table(name = "tb_address")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var street: String = "",
    var number: Int? = null,
    var district: String = "",
    var complement: String = ""
)
