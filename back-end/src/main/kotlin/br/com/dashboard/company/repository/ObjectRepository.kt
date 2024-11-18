package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.`object`.Object
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ObjectRepository : JpaRepository<Object, Long>
