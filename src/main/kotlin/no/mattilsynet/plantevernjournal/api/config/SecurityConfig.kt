package no.mattilsynet.plantevernjournal.api.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import java.util.function.Consumer


@Configuration
@EnableWebFluxSecurity
@Profile("staging", "prod", "local")
class SecurityConfig {

    private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)
    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http.csrf { it.disable() }
            .authorizeExchange { authorize ->
                authorize
                    .pathMatchers("/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()

                    .pathMatchers("/plantevernjournal/innlesing/v*")
                    .hasAuthority("Mattilsynet:plantevern.journal.innlesing")

                    .anyExchange().authenticated()
            }.build()

    @Bean
    fun authorityProbe(): WebFilter {
        return WebFilter { exchange: ServerWebExchange?, chain: WebFilterChain? ->
            ReactiveSecurityContextHolder.getContext()
                .also {
                    logger.error("STarten av auth sjekk")
                }
                .doOnNext(Consumer { ctx: SecurityContext? ->
                    val auth = ctx!!.authentication
                    if (auth != null) {
                        logger.error("Authorities: " + auth.authorities)
                    } else {
                        logger.error("Ingen auth")
                    }
                })
                .then<Void?>(chain!!.filter(exchange!!))
        }
    }
}
