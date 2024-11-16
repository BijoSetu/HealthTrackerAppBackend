package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object MileStones : Table("milestones") {

        val id = integer("id").autoIncrement()
        val userId = integer("user_id").references(Users.user_id).index()
        val milestoneName = varchar("milestone_name", 255)
        val achievedDate =  datetime("achieved_date").nullable()
        val notes = varchar("notes", 255).nullable()
        val createdAt = datetime("created_at").nullable()
        val updatedAt = datetime("updated_at").nullable()

}





