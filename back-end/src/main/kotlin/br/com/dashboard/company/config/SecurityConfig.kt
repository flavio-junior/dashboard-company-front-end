package br.com.dashboard.company.config

import br.com.dashboard.company.security.JwtTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    var securityFilter: JwtTokenFilter? = null

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .httpBasic { basic: HttpBasicConfigurer<HttpSecurity> -> basic.disable() }
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(
                        "/api/auth/v1/signIn",
                        "/api/auth/v1/recover-password",
                        "/api/auth/v1/check-recover-password/{code}",
                        "/api/auth/v1/new-password",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                    ).permitAll()
                    .requestMatchers("api/dashboard/company/categories/v1**").hasRole("ADMIN")
                    .requestMatchers("api/dashboard/company/foods/v1**").hasRole("ADMIN")
                    .requestMatchers("api/dashboard/company/items/v1**").hasRole("ADMIN")
                    .requestMatchers("api/dashboard/company/orders/v1**").hasRole("ADMIN")
                    .requestMatchers("api/dashboard/company/products/v1**").hasRole("ADMIN")
                    .requestMatchers("api/dashboard/company/reservations/v1**").hasRole("ADMIN")
                    .requestMatchers("/api/dashboard/company/checkout/v1**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .cors { _: CorsConfigurer<HttpSecurity?>? -> }
            .build()
    }
}
