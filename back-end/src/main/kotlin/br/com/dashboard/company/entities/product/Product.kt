package br.com.dashboard.company.entities.product

import br.com.dashboard.company.entities.category.Category
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
    var name: String = "",
    var description: String = "",
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "tb_product_category",
        joinColumns = [JoinColumn(name = "fk_product", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "fk_category", referencedColumnName = "id")]
    )
    var categories: MutableList<Category>? = null,
    var price: Double = 0.0,
    @Column(name = "stock_quantity", nullable = false)
    var quantity: Int? = 0
)
