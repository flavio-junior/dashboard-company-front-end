package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.order.JoinOrderAndObject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderObjectRepository : JpaRepository<JoinOrderAndObject, Long> {

    @Modifying
    @Query("DELETE FROM JoinOrderAndObject j WHERE j.keys.order = :idOrder AND j.keys.`object` = :idObject")
    fun deleteRelationBetween(
        @Param("idOrder") idOrder: Long,
        @Param("idObject") idObject: Long
    )
}
