package br.com.dashboard.company.controller

import br.com.dashboard.company.exceptions.ForbiddenActionRequestException
import br.com.dashboard.company.service.AuthService
import br.com.dashboard.company.utils.ConstantsUtils.EMPTY_FIELDS
import br.com.dashboard.company.utils.MediaType.APPLICATION_JSON
import br.com.dashboard.company.vo.user.SignInRequestVO
import br.com.dashboard.company.vo.user.TokenVO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/auth/v1"])
@Tag(name = "Auth", description = "EndPoint For Authentication")
class AuthController {

    @Autowired
    private lateinit var authService: AuthService

    @PostMapping(
        value = ["/signIn"],
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Authentication", description = "Authentication",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(schema = Schema(implementation = SignInRequestVO::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun signIn(@RequestBody signInVO: SignInRequestVO): ResponseEntity<TokenVO> {
        require(
            value = signInVO.email.isNotEmpty() && signInVO.email.isNotBlank() &&
                    signInVO.password.isNotEmpty() && signInVO.password.isNotBlank()
        ) {
            throw ForbiddenActionRequestException(exception = EMPTY_FIELDS)
        }
        return ResponseEntity.ok(authService.signIn(signInVO))
    }

    @PutMapping(
        value = ["/refresh/{email}"],
        consumes = [APPLICATION_JSON],
        produces = [APPLICATION_JSON]
    )
    @Operation(
        summary = "Refresh Token", description = "Refresh Token",
        tags = ["Auth"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Operation Unauthorized", responseCode = "403", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [
                    Content(schema = Schema(implementation = Unit::class))
                ]
            )
        ]
    )
    fun refreshToken(
        @PathVariable("email") email: String,
        @RequestHeader("Authorization") refreshToken: String,
    ): ResponseEntity<TokenVO> {
        return ResponseEntity.ok(authService.refreshToken(email, refreshToken))
    }
}
