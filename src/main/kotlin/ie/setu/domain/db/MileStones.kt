package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object MileStones : Table("milestones") {

        val id = integer("id").autoIncrement()
        val userId = integer("userid").references(Users.userId).index()
        val milestoneName = varchar("milestonename", 255)
        val achievedDate =  datetime("achieveddate").nullable()
        val notes = varchar("notes", 255).nullable()
        val createdAt = datetime("createdat").nullable()
        val updatedAt = datetime("updatedat").nullable()

}





