package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.food.Food
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FoodRepository : JpaRepository<Food, Long> {

    @Query("SELECT f FROM Food f WHERE f.name = :name")
    fun checkNameFoodAlreadyExists(@Param("name") name: String): Food?

    @Query(
        """
            SELECT f FROM Food f
            WHERE :name IS NULL OR LOWER(CAST(f.name AS string)) LIKE LOWER(CONCAT('%', :name, '%'))
        """
    )
    fun findAllFoods(@Param("name") name: String?, pageable: Pageable): Page<Food>?

    @Query(
        value = "SELECT f FROM Food f WHERE f.user.id = :userId AND LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))"
    )
    fun findFoodByName(
        @Param("userId") userId: Long,
        @Param("name") name: String
    ): List<Food>

    @Modifying
    @Query("UPDATE Food f SET f.price =:price WHERE f.id =:id")
    fun updatePriceFood(
        @Param("id") idFood: Long,
        @Param("price") price: Double
    )
}
