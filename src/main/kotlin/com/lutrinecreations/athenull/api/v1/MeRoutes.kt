package com.lutrinecreations.athenull.api.v1

import com.lutrinecreations.athenull.database.Devices
import com.lutrinecreations.athenull.database.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


@Serializable
data class MeResponse(
    val email: String? = null,
    val id: String? = null,
    val points: Int = 0,
    val regdate: Long = 0,
    val superuser: Boolean = false,
    val username: String? = null
)

@Serializable
data class DeviceResponse(
    val alias: String? = null,
    @SerialName("athena_host")
    val athenaHost: String = "athenull.local", // TODO Hardcoded for now
    @SerialName("device_type")
    val deviceType: String? = null,
    @SerialName("dongle_id")
    val dongleId: String? = null,
    @SerialName("ignore_uploads")
    val ignoreUploads: Boolean? = null,
    @SerialName("is_owner")
    val isOwner: Boolean = false,
    @SerialName("is_paired")
    val isPaired: Boolean = false,
    @SerialName("last_athena_ping")
    val lastAthenaPing: Long? = null,
    @SerialName("last_gps_accuracy")
    val lastGpsAccuracy: Double? = null,
    @SerialName("last_gps_bearing")
    val lastGpsBearing: Double? = null,
    @SerialName("last_gps_lat")
    val lastGpsLatitude: Double? = null,
    @SerialName("last_gps_lng")
    val lastGpsLongitude: Double? = null,
    @SerialName("last_gps_speed")
    val lastGpsSpeed: Double? = null,
    @SerialName("last_gps_time")
    val lastGpsTime: Long? =  null,
    @SerialName("openpilot_version")
    val openpilotVersion: String? = null,
    val prime: Boolean = true,
    @SerialName("prime_type")
    val primeType: Int? = null,
    @SerialName("public_key")
    val publicKey: String? = null,
    val serial: String? = null,
    @SerialName("sim_id")
    val simId: String? = null,
    @SerialName("trial_claimed")
    val trialClaimed: Boolean = true
)

enum class DeviceType(val id: Int, val label: String) {  // TODO Fix Device Types
    BLACK_PANDA(0, "black_panda"),
    WHITE_PANDA(1, "white_panda"),
    GREY_PANDA(2, "grey_panda"),
    UNO(3, "uno"),
    NEO(4, "neo");

    companion object {
        fun fromId(id: Int?): String? = entries.firstOrNull { it.id == id }?.label
    }
}

fun mapDeviceRow(row: ResultRow, userId: Long): DeviceResponse {
    return DeviceResponse(
        alias = row[Devices.alias],
        athenaHost = row[Devices.athenaHost] ?: "athenull.local",
        deviceType = DeviceType.fromId(row[Devices.deviceType]),
        dongleId = row[Devices.dongleId],
        ignoreUploads = null, // For some reason ignoreUploads is either null or true in API docs, maybe TODO implement real logic if anyone wants it
        isOwner = row[Devices.ownerId] == userId,
        isPaired = row[Devices.isPaired],
        lastAthenaPing = row[Devices.lastAthenaPing],
        lastGpsAccuracy = row[Devices.lastGpsAccuracy],
        lastGpsBearing = row[Devices.lastGpsBearing],
        lastGpsLatitude = row[Devices.lastGpsLat],
        lastGpsLongitude = row[Devices.lastGpsLng],
        lastGpsSpeed = row[Devices.lastGpsSpeed],
        lastGpsTime = row[Devices.lastGpsTime]?.toEpochMilliseconds(),
        openpilotVersion = row[Devices.openpilotVersion],
        prime = row[Devices.prime],
        primeType = row[Devices.primeType],
        publicKey = row[Devices.publicKey],
        serial = row[Devices.serial],
        simId = row[Devices.simId],
        trialClaimed = row[Devices.trialClaimed]
    )
}

fun ApplicationCall.jwtUserId(): Long? =
    principal<JWTPrincipal>()?.payload?.getClaim("user_id")?.asString()?.toLongOrNull()

fun Route.meRoutes() {
    authenticate("auth-jwt") {
        route("/v1/me") {
            get("/") {
                val userId = call.jwtUserId()

                if (userId == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    return@get
                }

                val user = transaction {
                    Users.select(Users.id eq userId, Users.deletedAt eq null).singleOrNull()
                } ?: return@get call.respond(HttpStatusCode.NotFound, "User not found")

                call.respond(
                    MeResponse(
                        email = user[Users.email],
                        id = user[Users.id].toString(),
                        points = user[Users.points],
                        regdate = user[Users.createdAt].epochSeconds,
                        superuser = user[Users.superuser],
                        username = user[Users.username]
                    )
                )
            }

            get("/devices/") {
                val userId = call.jwtUserId()

                if (userId == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    return@get
                }

                val devices: List<DeviceResponse> = transaction {
                    Devices.select(Devices.ownerId eq userId, Devices.deletedAt eq null).map { row ->
                        mapDeviceRow(row, userId)
                    }
                }

                // No need for 404, empty json array is expected behavior if no devices are registered/shared
                call.respond(devices)
            }

            get("/devices/{dongleId}") {
                val userId = call.jwtUserId()

                if (userId == null) {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                    return@get
                }

                val dongleId = call.parameters["dongleId"]
                if ( dongleId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Missing dongle ID")
                    return@get
                }

                val row = transaction {
                    Devices.select(Devices.dongleId eq dongleId, Devices.deletedAt eq null).singleOrNull()
                } ?: return@get call.respond(HttpStatusCode.NotFound, "Device not found")

                call.respond(mapDeviceRow(row, userId))
            }
        }
    }
}