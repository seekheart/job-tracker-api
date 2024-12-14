package dev.seekheart.jobtrackerapi.configs

import dev.seekheart.jobtrackerapi.users.services.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TokenBasedAuthFilter(
    private val userService: UserService
) : OncePerRequestFilter() {
    val ignoredMatchers: List<AntPathRequestMatcher> = listOf(
        AntPathRequestMatcher("/users/**", HttpMethod.POST.toString()),
    )

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return ignoredMatchers.any {
            it.matches(request)
        }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)
        if (token != null && userService.isValidToken(token)) {
            val user = userService.findByToken(token)
            if (user != null) {
                val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
                logger.debug("Setting authentication = $authentication")
                SecurityContextHolder.getContext().authentication = authentication
            }
        } else {
            logger.debug("Checking if skipping auth check for path = ${request.contextPath} method = ${request.method}")
            if (request.contextPath != "/users" && request.method != "POST") {
                response.status = 403
                return
            }
            return
        }
        filterChain.doFilter(request, response)
        return
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7) // Remove "Bearer " prefix
        } else {
            null
        }
    }

}