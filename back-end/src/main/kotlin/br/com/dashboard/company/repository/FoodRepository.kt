package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.food.Food
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FoodRepository : JpaRepository<Food, Long> {

    @Query("SELECT f FROM Food f WHERE f.name = :name OR f.description = :description")
    fun checkNameOrDescriptionFoodAlreadyExists(
        @Param("name") name: String,
        @Param("description") description: String
    ): Food?

    @Modifying
    @Query("UPDATE Food f SET f.price =:price WHERE f.id =:id")
    fun updatePriceFood(
        @Param("id") idFood: Long,
        @Param("price") price: Double
    )
}
