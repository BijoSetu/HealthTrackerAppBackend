package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object MileStones : Table("milestones") {

        val id = integer("id").autoIncrement()
        val userId = integer("userid").references(Users.userId).index()
        val milestoneName = varchar("milestonename", 255)
        val achievedDate =  datetime("achieveddate")
        val notes = varchar("notes", 255).nullable()
        val createdAt = datetime("createdat")
        val updatedAt = datetime("updatedat")

        override val primaryKey = PrimaryKey(id, name = "PK_Milestones_ID")
}





