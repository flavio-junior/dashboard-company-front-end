package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    @Query("SELECT c FROM Product c WHERE c.name = :name")
    fun checkNameProductAlreadyExists(@Param("name") name: String): Product?
}
