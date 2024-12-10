package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.`object`.Object
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ObjectRepository : JpaRepository<Object, Long> {

    @Query(value = "SELECT o FROM Object o WHERE o.user.id = :userId AND o.id = :objectId")
    fun findObjectById(
        @Param("userId") userId: Long,
        @Param("objectId") objectId: Long
    ): Object?

    @Modifying
    @Query(
        value = """
            UPDATE Object o SET
                o.quantity = o.quantity + :quantity, o.total = o.total + :total 
            WHERE
                o.user.id = :userId AND o.id = :objectId
            """
    )
    fun incrementMoreDataObject(
        @Param("userId") userId: Long,
        @Param("objectId") objectId: Long,
        @Param("quantity") quantity: Int? = 0,
        @Param("total") total: Double? = 0.0
    )

    @Modifying
    @Query("UPDATE Object o SET o.quantity = o.quantity - :quantity, o.total = o.total - :total WHERE o.id = :objectId")
    fun removeItemObject(
        @Param("objectId") objectId: Long,
        @Param("quantity") quantity: Int? = 0,
        @Param("total") total: Double? = 0.0
    )

    @Modifying
    @Query(value = "DELETE FROM Object o WHERE o.id = :objectId AND o.user.id = :userId")
    fun deleteObjectById(
        @Param("userId") userId: Long,
        @Param("objectId") objectId: Long
    ): Int
}
