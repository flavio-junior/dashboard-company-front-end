package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.item.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    @Query(value = "SELECT i FROM Item i WHERE i.user.id = :userId AND i.name = :name")
    fun checkNameItemAlreadyExists(
        @Param("userId") userId: Long,
        @Param("name") name: String
    ): Item?

    @Query(
        value =
            """
            SELECT i FROM Item i
                WHERE i.user.id = :userId
            AND (:name IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%')))
            """
    )
    fun findAllItems(
        @Param("userId") userId: Long,
        @Param("name") name: String?, pageable: Pageable
    ): Page<Item>?

    @Query(value = "SELECT i FROM Item i WHERE i.user.id = :userId AND i.id = :idItem")
    fun findItemById(
        @Param("userId") userId: Long,
        @Param("idItem") itemId: Long
    ): Item?

    @Query(
        value = "SELECT i FROM Item i WHERE i.user.id = :userId AND LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%'))"
    )
    fun findItemByName(
        @Param("userId") userId: Long,
        @Param("name") name: String?
    ): List<Item>

    @Modifying
    @Query(value = "UPDATE Item i SET i.price =:price WHERE i.user.id = :userId AND i.id =:idItem")
    fun updateItemPrice(
        @Param("userId") userId: Long,
        @Param("idItem") idItem: Long,
        @Param("price") price: Double
    )

    @Modifying
    @Query(value = "DELETE FROM Item i WHERE i.id = :itemId AND i.user.id = :userId")
    fun deleteItemById(
        @Param("userId") userId: Long,
        @Param("itemId") itemId: Long
    ): Int
}
