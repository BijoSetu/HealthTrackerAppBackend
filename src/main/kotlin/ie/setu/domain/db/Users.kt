package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

// SRP - Responsibility is to manage one user.
//       Database wise, this is the table object.

object Users : Table("users") {
    val userId = integer("user_id").autoIncrement()
    val name = varchar("name", 100)
    val email = varchar("email", 255,)
    val password=varchar("password", 32)

    override val primaryKey = PrimaryKey(userId, name = "PK_Users_ID")
}

