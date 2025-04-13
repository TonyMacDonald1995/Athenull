package com.lutrinecreations.athenull.config

import com.charleskorn.kaml.Yaml
import java.io.File

object ConfigLoader {
    private const val CONFIG_PATH = "config.yaml"

    fun load(): AppConfig {
        val configFile = File(CONFIG_PATH)

        if (!configFile.exists()) {
            println("No config.yaml found, creating default...")
            val default = AppConfig()
            save(default)
            return default
        }

        return Yaml.default.decodeFromString(AppConfig.serializer(), configFile.readText())
    }

    private fun save(config: AppConfig) {
        val text = Yaml.default.encodeToString(AppConfig.serializer(), config)
        File(CONFIG_PATH).writeText(text)
        println("Wrote default config.yaml to $CONFIG_PATH")
    }
}
