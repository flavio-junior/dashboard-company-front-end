package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.item.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    @Query("SELECT c FROM Item c WHERE c.name = :name")
    fun checkNameItemAlreadyExists(@Param("name") name: String): Item?

    @Modifying
    @Query("UPDATE Item p SET p.price =:price WHERE p.id =:id")
    fun updatePriceItem(@Param("id") idItem: Long, @Param("price") price: Double)

}
