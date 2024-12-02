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

    @Query(
    """
        SELECT c FROM Category c
            WHERE :name IS NULL OR LOWER(CAST(c.name AS string)) LIKE LOWER(CONCAT('%', :name, '%'))
    """
    )
    fun findAllCategories(@Param("name") name: String?, pageable: Pageable): Page<Category>?

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    fun findCategoryByName(name: String): List<Category>
}
