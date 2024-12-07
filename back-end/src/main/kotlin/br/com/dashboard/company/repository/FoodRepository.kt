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

    @Query("SELECT f FROM Food f WHERE f.user.id = :userId AND f.name = :foodName")
    fun checkNameFoodAlreadyExists(
        @Param("userId") userId: Long,
        @Param("foodName") foodName: String
    ): Food?

    @Query(
        value = """
        SELECT f FROM Food f
            WHERE f.user.id = :userId
        AND (:foodName IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :foodName, '%')))
    """
    )
    fun findAllFoods(
        @Param("userId") userId: Long,
        @Param("foodName") foodName: String?,
        pageable: Pageable
    ): Page<Food>?

    @Query(value = "SELECT f FROM Food f WHERE f.user.id = :userId AND f.id = :foodId")
    fun findFoodById(
        @Param("userId") userId: Long,
        @Param("foodId") foodId: Long
    ): Food?

    @Query(
        value = "SELECT f FROM Food f WHERE f.user.id = :userId AND LOWER(f.name) LIKE LOWER(CONCAT('%', :foodName, '%'))"
    )
    fun findFoodByName(
        @Param("userId") userId: Long,
        @Param("foodName") foodName: String
    ): List<Food>

    @Modifying
    @Query("UPDATE Food f SET f.price =:price WHERE f.user.id = :userId AND f.id =:id")
    fun updatePriceFood(
        @Param("userId") userId: Long,
        @Param("id") idFood: Long,
        @Param("price") price: Double
    )

    @Modifying
    @Query(value = "DELETE FROM Food f WHERE f.id = :foodId AND f.user.id = :userId")
    fun deleteFoodById(
        @Param("userId") userId: Long,
        @Param("foodId") foodId: Long
    ): Int
}
