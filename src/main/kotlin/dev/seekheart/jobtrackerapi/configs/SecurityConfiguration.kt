package dev.seekheart.jobtrackerapi.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(val tokenBasedAuthFilter: TokenBasedAuthFilter) {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
        }.addFilterBefore(tokenBasedAuthFilter, BasicAuthenticationFilter::class.java)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .csrf {
                it.disable()
            }
        return http.build()
    }
}