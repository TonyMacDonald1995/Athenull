package com.lutrinecreations.athenull.database

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Users : LongIdTable("users") {
    val githubUserId = long("git_hub_user_id").nullable()
    val googleUserId = varchar("google_user_id", 255).nullable()
    val customUserId = long("custom_user_id").nullable()
    val email = varchar("email", 255).nullable()
    val points = integer("points").default(0)
    val superuser = bool("superuser").default(false)
    val username = varchar("username", 64).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)
    val updatedAt = timestamp("updated_at").nullable()
    val deletedAt = timestamp("deleted_at").nullable()
}