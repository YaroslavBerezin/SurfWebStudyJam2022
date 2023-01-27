package ru.surf.auth.service.impl

import com.auth0.jwk.Jwk
import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import ru.surf.auth.service.JwtService
import java.net.URL
import java.security.interfaces.RSAPublicKey
import java.util.*


@Service
class JwtServiceImpl(@Autowired
                     @Lazy
                     private val keycloakJwk: KeycloakJwk,

                     @Value("\${keycloak.certs-url}")
                     private val jwkUrl: String,

                     @Value("\${keycloak.certs-id}")
                     private val certsId: String) : JwtService {
    override fun validateJwt(token: String): DecodedJWT {
        val jwt = JWT.decode(token)
        Algorithm.RSA256(keycloakJwk.get.publicKey as RSAPublicKey).verify(jwt)
        return if (jwt.expiresAt.before(Date())) throw Exception("token is expired") else jwt
    }

    @Component
    @Lazy
    class KeycloakJwk @Autowired constructor(private val jwtServiceImpl: JwtServiceImpl) {
        @get:Cacheable(cacheNames = ["KeycloakJwk"])
        val get: Jwk
            get() {
                with(jwtServiceImpl) {
                    return UrlJwkProvider(URL(jwkUrl))[certsId]
                }
            }
    }
}
