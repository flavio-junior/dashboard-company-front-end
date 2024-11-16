package br.com.dashboard.company.entities.items

import br.com.dashboard.company.utils.TypeItem
import jakarta.persistence.*

@Entity
@Table(name = "tb_item")
data class Items(
    var id: Long = 0,
    @Enumerated(EnumType.STRING)
    var type: TypeItem,
    var name: String = "",
    var items: Int = 0
)
