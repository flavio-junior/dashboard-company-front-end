package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.item.Item
import br.com.dashboard.company.entities.reservation.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.name = :name")
    fun checkNameItemAlreadyExists(@Param("name") name: String): Item?

    @Modifying
    @Query("UPDATE Item i SET i.price =:price WHERE i.id =:id")
    fun updatePriceItem(@Param("id") idItem: Long, @Param("price") price: Double)

    @Query(
        """
        SELECT i FROM Item i
            WHERE :name IS NULL OR LOWER(CAST(i.name AS string)) LIKE LOWER(CONCAT('%', :name, '%'))
    """
    )
    fun findAllItems(@Param("name") name: String?, pageable: Pageable): Page<Item>?
}
