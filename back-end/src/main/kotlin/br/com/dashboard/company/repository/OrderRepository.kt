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

    @Query("SELECT o from Order o WHERE o.status =:status")
    fun findAllOrdersOpen(
        @Param("status") status: Status,
        pageable: Pageable
    ): Page<Order>?

    @Modifying
    @Query("UPDATE Order o SET o.status =:status WHERE o.id =:id")
    fun updateStatusOrder(
        @Param("id") idOrder: Long,
        @Param("status") status: Status,
    )
}
