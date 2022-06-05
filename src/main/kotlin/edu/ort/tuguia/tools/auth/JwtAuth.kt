package edu.ort.tuguia.tools.auth

import edu.ort.tuguia.tools.helpers.http.ApiException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val CookieKey = "jwt"
private const val SecretKey = "secret"
private const val AuthHeader = "Authorization"
private const val TokenPrefix = "Bearer "

class JwtAuth {
    companion object {
        fun initializeToken(username: String, response: HttpServletResponse) {
            val jwtToken = Jwts.builder()
                .setIssuer(username)
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

        fun getUsernameFromRequest(request: HttpServletRequest): String {
            val jwtToken = this.getTokenFromCookies(request)
                ?: throw ApiException(HttpStatus.UNAUTHORIZED, "User not found in context")
            return this.getUsernameFromToken(jwtToken)
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

        private fun getUsernameFromToken(jwtToken: String): String {
            return Jwts.parser()
                .setSigningKey(SecretKey)
                .parseClaimsJws(jwtToken).body.issuer
        }
    }
}