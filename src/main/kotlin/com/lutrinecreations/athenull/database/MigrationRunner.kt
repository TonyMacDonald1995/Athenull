package com.lutrinecreations.athenull.database

import com.lutrinecreations.athenull.config.AppConfig
import org.flywaydb.core.Flyway

object MigrationRunner {
    fun migrate(config: AppConfig) {
        val db = config.database
        val url= when (db.type.lowercase()) {
            "sqlite" -> "jdbc:sqlite:${db.file}"
            "mysql" -> "jdbc:mysql://${db.host}:${db.port}/${db.name}?useSSL=false"
            "postgres" -> "jdbc:postgresql://${db.host}:${db.port}/${db.name}"
            else -> error("Unsupported DB type: ${db.type}")
        }

        val flyway = Flyway.configure()
            .dataSource(url, db.user, db.password)
            .locations("classpath:db/migration")
            .baselineOnMigrate(true)
            .load()

        flyway.migrate()
    }
}