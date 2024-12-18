package br.com.dashboard.company.service

import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import br.com.dashboard.company.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService: UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        val user: UserDetails? = userRepository.findByEmail(username)
        return user ?: throw UsernameNotFoundException("$username not found")
    }

    fun findUserById(id: Long): User? {
       return userRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("User with id $id not found")
        }
    }
}