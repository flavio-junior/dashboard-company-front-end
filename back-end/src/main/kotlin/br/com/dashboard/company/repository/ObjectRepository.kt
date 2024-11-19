package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.`object`.Object
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ObjectRepository : JpaRepository<Object, Long> {

    @Modifying
    @Query("UPDATE Object o SET o.quantity = o.quantity + :quantity, o.total = o.total + :total WHERE o.id = :id")
    fun updateObject(
        @Param("id") id: Long,
        @Param("quantity") quantity: Int? = 0,
        @Param("total") total: Double? = 0.0
    )

    @Modifying
    @Query("UPDATE Object o SET o.quantity = o.quantity - :quantity, o.total = o.total - :total WHERE o.id = :id")
    fun removeItemObject(
        @Param("id") id: Long,
        @Param("quantity") quantity: Int? = 0,
        @Param("total") total: Double? = 0.0
    )
}
