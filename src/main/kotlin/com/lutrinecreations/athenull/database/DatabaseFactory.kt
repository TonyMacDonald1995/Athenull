package com.lutrinecreations.athenull.database

import com.lutrinecreations.athenull.config.AppConfig
import com.lutrinecreations.athenull.config.DatabaseConfig
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun connect(config: AppConfig) {
        val db = config.database
        when (db.type.lowercase()) {
            "sqlite"   -> connectSqlite(db)
            "mysql"    -> connectMySQL(db)
            "postgres" -> connectPostgres(db)
            else       -> error("Unsupported database type: ${db.type}")
        }

    }

    private fun connectSqlite(db: DatabaseConfig) {
        println("Connecting to SQLite at ${db.file}")
        Database.connect("jdbc:sqlite:${db.file}", driver = "org.sqlite.JDBC")
    }

    private fun connectMySQL(db: DatabaseConfig) {
        val url = "jdbc:mysql://${db.host}:${db.port}/${db.name}?useSSL=false&serverTimezone=UTC"
        println("Connecting to MySQL at $url")
        Database.connect(url, driver = "com.mysql.cj.jdbc.Driver", user = db.user, password = db.password)
    }

    private fun connectPostgres(db: DatabaseConfig) {
        val url = "jdbc:postgresql://${db.host}:${db.port}/${db.name}"
        println("Connecting to PostgreSQL at $url")
        Database.connect(url, driver = "org.postgresql.Driver", user = db.user, password = db.password)
    }
}