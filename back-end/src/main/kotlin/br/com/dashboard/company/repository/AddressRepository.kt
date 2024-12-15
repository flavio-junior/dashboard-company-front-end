package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.address.Address
import br.com.dashboard.company.utils.common.AddressStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaRepository<Address, Long> {

    @Query(value = "SELECT a FROM Address a WHERE a.id = :addressId AND a.order.id = :orderId")
    fun findAddressById(
        @Param("addressId") addressId: Long,
        @Param("orderId") orderId: Long
    ): Address?

    @Modifying
    @Query("UPDATE Address a SET a.status =:status WHERE a.id = :addressId AND a.id =:orderId")
    fun updateStatusDelivery(
        @Param("orderId") orderId: Long,
        @Param("addressId") addressId: Long,
        @Param("status") status: AddressStatus
    )
}
