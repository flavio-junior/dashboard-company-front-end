package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.order.Order
import br.com.dashboard.company.utils.common.Status
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

    @Query("SELECT o from Order o WHERE o.user.id = :userId AND o.status =:status")
    fun findAllOrdersOpen(
        @Param("userId") userId: Long,
        @Param("status") status: Status,
        pageable: Pageable
    ): Page<Order>?

    @Modifying
    @Query("UPDATE Order o SET o.status =:status WHERE o.user.id = :userId AND o.id =:orderId")
    fun updateStatusOrder(
        @Param("userId") userId: Long,
        @Param("orderId") orderId: Long,
        @Param("status") status: Status
    )

    @Query(value = "SELECT o FROM Order o WHERE o.user.id = :userId AND o.id = :orderId")
    fun findOrderById(
        @Param("userId") userId: Long,
        @Param("orderId") orderId: Long
    ): Order?

    @Modifying
    @Query("UPDATE Order o SET o.quantity = o.quantity + :quantity WHERE o.id = :orderId")
    fun updateQuantityOrder(
        @Param("orderId") orderId: Long,
        @Param("quantity") quantity: Int? = 0
    )

    @Modifying
    @Query("UPDATE Order o SET o.total = o.total + :total WHERE o.id = :orderId")
    fun incrementDataOrder(
        @Param("orderId") orderId: Long,
        @Param("total") total: Double? = 0.0
    )

    @Modifying
    @Query("UPDATE Order o SET o.quantity = o.quantity - :quantity, o.total = o.total - :total WHERE o.id = :orderId")
    fun decrementDataOrder(
        @Param("orderId") orderId: Long,
        @Param("quantity") quantity: Int? = 0,
        @Param("total") total: Double? = 0.0
    )

    @Modifying
    @Query(value = "DELETE FROM Order o WHERE o.id = :orderId AND o.user.id = :userId")
    fun deleteOrderById(
        @Param("userId") userId: Long,
        @Param("orderId") orderId: Long
    ): Int
}
