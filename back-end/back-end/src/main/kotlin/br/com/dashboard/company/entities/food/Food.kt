package br.com.dashboard.company.entities.food

import br.com.dashboard.company.entities.category.Category
import br.com.dashboard.company.entities.user.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "tb_food")
data class Food(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null,
    var name: String = "",
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_food_category",
        joinColumns = [JoinColumn(name = "fk_food", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_category", referencedColumnName = "id")]
    )
    var categories: MutableList<Category>? = null,
    var price: Double = 0.0,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_user_food",
        joinColumns = [JoinColumn(name = "fk_food", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_user", referencedColumnName = "id")]
    )
    var user: User? = null
)
