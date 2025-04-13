package com.lutrinecreations.athenull

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lutrinecreations.athenull.api.v1.meRoutes
import com.lutrinecreations.athenull.config.AppConfig
import com.lutrinecreations.athenull.config.ConfigLoader
import com.lutrinecreations.athenull.database.DatabaseFactory
import com.lutrinecreations.athenull.database.MigrationRunner
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.*

fun main() {
    val config = ConfigLoader.load()

    MigrationRunner.migrate(config)
    DatabaseFactory.connect(config)

    embeddedServer(
        Netty,
        port = config.server.port,
        host = config.server.host
    ) {
        this.attributes.put(AttributeKey("AppConfig"), config)
        module()
    }.start(wait = true)
}

fun Application.module() {
    val config = this.attributes[AttributeKey<AppConfig>("AppConfig")]
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        json()
    }

    install(Authentication) {
        jwt("auth-jwt") {
            realm = "Athenull"
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.server.jwt))
                    .withIssuer("athenull")
                    .build()
            )

            validate { credential ->
                val userId = credential.payload.getClaim("user_id").asString()
                if (userId != null) JWTPrincipal(credential.payload) else null
            }
        }
    }

    routing {
        get("/") {
            call.respondText("Athenull backend server is up and running!")
        }

        meRoutes()
    }
}
