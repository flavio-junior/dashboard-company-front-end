package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.category.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long?> {

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    fun checkNameCategoryAlreadyExists(@Param("name") name: String): Category?

    @Query("SELECT c FROM Category c")
    fun findAllCategories(pageable: Pageable): Page<Category>?
}
