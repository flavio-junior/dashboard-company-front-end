package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.menu.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : JpaRepository<Menu, Long>
