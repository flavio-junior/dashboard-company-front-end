package br.com.dashboard.company.repository

import br.com.dashboard.company.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User?, Long?>  {

    fun findByEmail(email: String?): UserDetails?

    @Query("SELECT u FROM User u WHERE u.email =:email")
    fun fetchByEmail(email: String?): User?
    fun id(id: Long): MutableList<User>
}