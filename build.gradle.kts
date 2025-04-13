plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    application
}

group = "com.lutrinecreations"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:3.1.2")
    implementation("io.ktor:ktor-server-netty:3.1.2")
    implementation("io.ktor:ktor-server-call-logging:3.1.2")
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.61.0")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("com.charleskorn.kaml:kaml:0.55.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    implementation("org.flywaydb:flyway-core:11.7.0")
    implementation("io.ktor:ktor-server-auth:3.1.2")
    implementation("io.ktor:ktor-server-auth-jwt:3.1.2")
    implementation("io.ktor:ktor-server-content-negotiation:3.1.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")



    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.lutrinecreations.athenull.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
