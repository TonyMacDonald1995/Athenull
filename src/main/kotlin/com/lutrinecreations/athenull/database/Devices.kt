package com.lutrinecreations.athenull.database

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Devices : LongIdTable("devices") {
    val alias = text("alias").nullable()
    val athenaHost = text("athena_host").nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val deletedAt = timestamp("deleted_at").nullable()
    val destinationLatitude = double("destination_latitude").nullable()
    val destinationLongitude = double("destination_longitude").nullable()
    val destinationPlaceDetails = text("destination_place_details").nullable()
    val destinationPlaceName = text("destination_place_name").nullable()
    val destinationSet = bool("destination_set").nullable()
    val deviceType = integer("device_type").nullable()
    val dongleId = text("dongle_id").nullable()
    val isPaired = bool("is_paired").default(false)
    val lastAthenaPing = long("last_athena_ping").nullable()
    val lastGpsAccuracy = double("last_gps_accuracy").nullable()
    val lastGpsBearing = double("last_gps_bearing").nullable()
    val lastGpsLat = double("last_gps_lat").nullable()
    val lastGpsLng = double("last_gps_lng").nullable()
    val lastGpsSpeed = double("last_gps_speed").nullable()
    val lastGpsTime = timestamp("last_gps_time").nullable()
    val openpilotVersion = text("open_pilot_version").nullable()
    val ownerId = long("owner_id").nullable()
    val prime = bool("prime").default(true)
    val primeType = integer("prime_type").default(1)
    val publicKey = text("public_key").nullable()
    val serial = text("serial").nullable()
    val simId = text("sim_id").nullable()
    val simType = long("sim_type").nullable()
    val trialClaimed = bool("trial_claimed").default(true)
    val updatedAt = timestamp("updated_at").nullable()
}