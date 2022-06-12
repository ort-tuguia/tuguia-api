package edu.ort.tuguia.tools.auth

import edu.ort.tuguia.tools.helpers.http.ApiException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val CookieKey = "jwt"
private const val RoleKey = "role"
private const val SecretKey = "secret"
private const val AuthHeader = "Authorization"
private const val TokenPrefix = "Bearer "

class JwtAuth {
    class LoggedUser(val username: String, val role: String) {
        fun hasRole(role: String): Boolean {
            return this.role == role
        }
        fun hasAnyRole(roles: List<String>): Boolean {
            roles.forEach {
                if (this.role == it) return true
            }
            return false
        }
    }

    companion object {
        fun initializeToken(username: String, role: String, response: HttpServletResponse) {
            val jwtToken = Jwts.builder()
                .setIssuer(username)
                .claim(RoleKey, role)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, SecretKey).compact()

            val jwtCookie = Cookie(CookieKey, jwtToken)
            response.addCookie(jwtCookie)
            response.addHeader(AuthHeader, TokenPrefix + jwtToken)
        }

        fun clearToken(response: HttpServletResponse) {
            val jwtCookie = Cookie(CookieKey, "")
            response.addCookie(jwtCookie)
        }

        fun getUserFromRequest(request: HttpServletRequest): LoggedUser {
            val jwtToken = this.getTokenFromRequest(request)
                ?: throw ApiException(HttpStatus.UNAUTHORIZED, "User not found in context")
            return this.getUserFromToken(jwtToken)
        }

        fun getTokenFromRequest(request: HttpServletRequest): String? {
            var jwtToken = this.getTokenFromCookies(request)
            if (jwtToken.isNullOrEmpty()) {
                jwtToken = this.getTokenFromHeaders(request)
            }
            return jwtToken
        }

        private fun getTokenFromCookies(request: HttpServletRequest): String? {
            if (request.cookies.isNullOrEmpty()) {
                return null
            }

            for (i in 0 until request.cookies.size) {
                val cookie = request.cookies[i]
                if (cookie.name == CookieKey) {
                    return cookie.value
                }
            }

            return null
        }

        private fun getTokenFromHeaders(request: HttpServletRequest): String? {
            val authHeader = request.getHeader(AuthHeader)
            if (authHeader.isNullOrEmpty() || !authHeader.startsWith(TokenPrefix)) {
                return null
            }

            return authHeader.removePrefix(TokenPrefix)
        }

        private fun getUserFromToken(jwtToken: String): LoggedUser {
            try {
                val body = Jwts.parser()
                    .setSigningKey(SecretKey)
                    .parseClaimsJws(jwtToken).body
                return LoggedUser(body.issuer, body[RoleKey] as String)
            } catch (e: ExpiredJwtException) {
                throw ApiException(HttpStatus.BAD_REQUEST, "Sesión expirada. Se debe realizar login nuevamente")
            }
        }
    }
}