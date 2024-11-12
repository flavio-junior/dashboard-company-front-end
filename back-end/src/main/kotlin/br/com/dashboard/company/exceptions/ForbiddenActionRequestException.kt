package br.com.dashboard.company.exceptions

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenActionRequestException(exception: String) : AuthenticationException(exception)
