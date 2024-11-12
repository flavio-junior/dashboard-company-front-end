package br.com.dashboard.company.entities.category

import jakarta.persistence.*

@Entity
@Table(name = "tb_category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false, unique = true)
    var name: String? = ""
)
