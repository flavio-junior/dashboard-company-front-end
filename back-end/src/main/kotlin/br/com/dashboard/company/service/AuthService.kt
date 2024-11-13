package br.com.dashboard.company.service

import br.com.dashboard.company.entities.user.User
import br.com.dashboard.company.exceptions.ForbiddenActionRequestException
import br.com.dashboard.company.repository.UserRepository
import br.com.dashboard.company.security.JwtTokenProvider
import br.com.dashboard.company.vo.user.SignInRequestVO
import br.com.dashboard.company.vo.user.TokenVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    fun signIn(signInVO: SignInRequestVO): TokenVO {
        return try {
            val authentication: Authentication =
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        signInVO.email,
                        signInVO.password
                    )
                )
            val user: User? = userRepository.fetchByEmail(email = signInVO.email)
            tokenProvider.createAccessToken(username = authentication.name, typeAccount = user?.typeAccount!!)
        } catch (e: AuthenticationException) {
            throw ForbiddenActionRequestException(exception = "invalid credentials")
        }
    }

    fun refreshToken(email: String, refreshToken: String): TokenVO {
        val user: User? = userRepository.fetchByEmail(email)
        val tokenResponse: TokenVO = if (user != null) {
            tokenProvider.refreshToken(refreshToken)
        } else {
            throw UsernameNotFoundException("Username $email not found")
        }
        return (tokenResponse)
    }
}
