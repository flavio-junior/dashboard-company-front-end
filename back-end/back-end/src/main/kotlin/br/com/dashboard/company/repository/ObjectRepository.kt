package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.`object`.Object
import br.com.dashboard.company.utils.common.ObjectStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ObjectRepository : JpaRepository<Object, Long> {

    @Query(value = "SELECT o FROM Object o WHERE o.order.id = :orderId AND o.id = :objectId")
    fun findObjectById(
        @Param("orderId") orderId: Long,
        @Param("objectId") objectId: Long
    ): Object?

    @Modifying
    @Query(
        value = """
            UPDATE Object o SET
                o.status = :status 
            WHERE
                o.order.id = :orderId AND o.id = :objectId
            """
    )
    fun updateStatusObject(
        @Param("orderId") orderId: Long,
        @Param("objectId") objectId: Long,
        @Param("status") status: ObjectStatus? = null
    )

    @Modifying
    @Query(
        value = """
            UPDATE Object o SET
                o.quantity = o.quantity + :quantity, o.total = o.total + :total 
            WHERE
                o.order.id = :orderId AND o.id = :objectId
            """
    )
    fun incrementMoreItemsObject(
        @Param("orderId") orderId: Long,
        @Param("objectId") objectId: Long,
        @Param("quantity") quantity: Int? = 0,
        @Param("total") total: Double? = 0.0
    )

    @Modifying
    @Query(
        value = """
            UPDATE Object o SET o.quantity = o.quantity - :quantity, o.total = o.total - :total 
            WHERE
                o.order.id = :orderId AND o.id = :objectId
            """
    )
    fun decrementItemsObject(
        @Param("orderId") orderId: Long,
        @Param("objectId") objectId: Long,
        @Param("quantity") quantity: Int? = 0,
        @Param("total") total: Double? = 0.0
    )

    @Modifying
    @Query(value = "DELETE FROM Object o WHERE o.id = :objectId AND o.order.id = :orderId")
    fun deleteObjectById(
        @Param("orderId") orderId: Long,
        @Param("objectId") objectId: Long
    ): Int
}
