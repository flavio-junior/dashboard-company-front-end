package br.com.dashboard.company.entities.`object`

import br.com.dashboard.company.utils.common.TypeItem
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_object")
data class Object(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Enumerated(EnumType.STRING)
    var type: TypeItem,
    var name: String = "",
    var items: Int = 0
)
