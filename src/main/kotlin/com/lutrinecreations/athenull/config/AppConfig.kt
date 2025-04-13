package com.lutrinecreations.athenull.config

import kotlinx.serialization.Serializable

@Serializable
data class ServerConfig(
    val host: String = "0.0.0.0",
    val port: Int = 80,
    val jwt: String = "changeme"
)

@Serializable
data class DatabaseConfig(
    val type:     String = "sqlite", // sqlite, mysql, postgres
    val host:     String = "localhost",
    val port:     Int    = 5432,
    val name:     String = "athenull",
    val user:     String = "athenull",
    val password: String = "changeme",
    val file:     String = "athenull.db"
)

@Serializable
data class AppConfig (
    val server: ServerConfig = ServerConfig(),
    val database: DatabaseConfig = DatabaseConfig()
)