package br.com.dashboard.company.security

import br.com.dashboard.company.exceptions.InvalidJwtAuthenticationException
import br.com.dashboard.company.vo.user.TokenVO
import br.com.dashboard.company.utils.common.TypeAccount
import br.com.dashboard.company.utils.others.toDate
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JwtTokenProvider {

    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey = "secret"

    @Value("\${security.jwt.token.expire-length:3600000}")
    private var validityInMilliseconds: Long = 3_600_000

    @Autowired
    private lateinit var userDetailsService: UserDetailsService
    private lateinit var algorithm: Algorithm

    @PostConstruct
    internal fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        algorithm = Algorithm.HMAC256(secretKey.toByteArray())
    }

    fun createAccessToken(username: String, typeAccount: TypeAccount): TokenVO {
        val now = LocalDateTime.now().withNano(0)
        val validity = now.plus(validityInMilliseconds, ChronoUnit.MILLIS).withNano(0)
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val createdFormatted = now.format(formatter)
        val expirationFormatted = validity.format(formatter)
        val accessToken = getAccessToken(username, typeAccount, now, validity)
        val refreshToken = getRefreshToken(username, typeAccount, now)
        return TokenVO(
            user = username,
            authenticated = true,
            type = typeAccount,
            accessToken = accessToken,
            refreshToken = refreshToken,
            created = createdFormatted,
            expiration = expirationFormatted
        )
    }

    fun refreshToken(refreshToken: String): TokenVO {
        var token: String = ""
        if (refreshToken.contains("Bearer ")) token = refreshToken.substring("Bearer ".length)
        val verifier: JWTVerifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(token)
        val username: String = decodedJWT.subject
        val typeAccount: TypeAccount = decodedJWT.getClaim("type_account").`as`(TypeAccount::class.java)
        return createAccessToken(username, typeAccount)
    }

    fun getAccessToken(
        username: String,
        typeAccount: TypeAccount,
        now: LocalDateTime,
        validity: LocalDateTime
    ): String {
        val issuerURL: String = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
        return JWT.create()
            .withClaim("type_account", typeAccount.toString())
            .withIssuedAt(now.toDate())
            .withExpiresAt(validity.toDate())
            .withSubject(username)
            .withIssuer(issuerURL)
            .sign(algorithm)
            .trim()
    }

    fun getRefreshToken(username: String, typeAccount: TypeAccount, now: LocalDateTime): String {
        val validRefreshToken = now.plus(validityInMilliseconds * 3, ChronoUnit.MILLIS)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .let { Date.from(it) }
        return JWT.create()
            .withClaim("type_account", typeAccount.toString())
            .withExpiresAt(validRefreshToken)
            .withSubject(username)
            .sign(algorithm)
            .trim()
    }

    fun getAuthentication(token: String): Authentication {
        val decodedJWT: DecodedJWT = decodedToken(token)
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(decodedJWT.subject)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun decodedToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secretKey.toByteArray())
        val verify: JWTVerifier = JWT.require(algorithm).build()
        return verify.verify(token)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring("Bearer ".length)
        } else {
            null
        }
    }

    fun validateToken(token: String): Boolean {
        try {
            val decodedJWT = decodedToken(token)
            if (decodedJWT.expiresAt.before(Date())) {
                throw InvalidJwtAuthenticationException("Token has expired")
            }
            return true
        } catch (e: TokenExpiredException) {
            throw e
        } catch (e: Exception) {
            throw InvalidJwtAuthenticationException("Expired or invalid JWT token!")
        }
    }
}
