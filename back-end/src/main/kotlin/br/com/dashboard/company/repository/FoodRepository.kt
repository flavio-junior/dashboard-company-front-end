package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.food.Food
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FoodRepository : JpaRepository<Food, Long> {

    @Query("SELECT c FROM Food c WHERE c.name = :name")
    fun checkNameFoodAlreadyExists(@Param("name") name: String): Food?

    @Modifying
    @Query("UPDATE Food p SET p.price =:price WHERE p.id =:id")
    fun updatePriceFood(
        @Param("id") idFood: Long,
        @Param("price") price: Double
    )

    @Modifying
    @Query("UPDATE Food p SET p.quantity = p.quantity + :quantity WHERE p.id = :id")
    fun restockFood(
        @Param("id") idFood: Long,
        @Param("quantity") quantity: Int
    )    
}
