package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object DailyGoals : Table("dailygoals") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(
        Users.user_id)
    val goalName = varchar("goal_name", 255)
    val goalDescription = varchar("goal_description", 255)
    val isCompleted = bool("is_completed").default(false)
    val createdAt = datetime("created_at").nullable()
    val date = datetime("date").nullable()
    val priority = integer("priority")
    val notes = text("notes")
}
