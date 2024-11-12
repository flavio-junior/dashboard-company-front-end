package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.category.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>
