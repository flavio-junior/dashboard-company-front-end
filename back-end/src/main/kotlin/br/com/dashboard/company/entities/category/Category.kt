package br.com.dashboard.company.entities.category

import br.com.dashboard.company.entities.user.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tb_category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "name", nullable = false, unique = true)
    var name: String = "",
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_user_category",
        joinColumns = [JoinColumn(name = "fk_category", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_user", referencedColumnName = "id")]
    )
    var user: User? = null
)
