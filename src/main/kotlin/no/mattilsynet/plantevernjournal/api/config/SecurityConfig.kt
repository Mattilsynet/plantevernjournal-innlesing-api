package no.mattilsynet.plantevernjournal.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@Profile("staging", "prod")
class SecurityConfig {
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
}
