package br.com.dashboard.company.security

import br.com.dashboard.company.exceptions.InvalidJwtAuthenticationException
import com.auth0.jwt.exceptions.TokenExpiredException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = tokenProvider.resolveToken(request)
        try {
            if (!token.isNullOrBlank() && tokenProvider.validateToken(token)) {
                val auth: Authentication = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (e: TokenExpiredException) {
            response.contentType = "application/json"
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.writer.write(
                """
                {
                  "status": 403,
                  "message": "Token expired, please login again."
                }
                """.trimIndent()
            )
            return
        } catch (e: InvalidJwtAuthenticationException) {
            response.contentType = "application/json"
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write(
                """
                {
                  "status": 401,
                  "message": "Invalid token."
                }
                """.trimIndent()
            )
            return
        }
        filterChain.doFilter(request, response)
    }
}
