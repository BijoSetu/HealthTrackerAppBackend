package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object DailyGoals : Table("dailygoals") {
    val id = integer("id").autoIncrement()
    val userId = integer("userid").references(
        Users.userId)
    val goalName = varchar("goalname", 255)
    val goalDescription = varchar("goaldescription", 255)
    val isCompleted = bool("iscompleted").default(false)
    val createdAt = datetime("createdat")
    val date = datetime("date")
    val priority = integer("priority")
    val notes = text("notes")

    override val primaryKey = PrimaryKey(id, name = "PK_dailygoals_ID")
}
