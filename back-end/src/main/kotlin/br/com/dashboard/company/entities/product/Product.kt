package br.com.dashboard.company.entities.product

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "tb_product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false, unique = true)
    var createdAt: Instant? = null,
    var name: String? = ""
)
