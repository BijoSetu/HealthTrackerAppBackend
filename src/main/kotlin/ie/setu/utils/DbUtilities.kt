package ie.setu.utils
import ie.setu.domain.DailyGoal
import ie.setu.domain.db.Users
import ie.setu.domain.User
import ie.setu.domain.db.DailyGoals
import org.jetbrains.exposed.sql.ResultRow
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun mapToUser(it: ResultRow) = User(
    user_id = it[Users.user_id],
    name = it[Users.name],
    email = it[Users.email],password = it[Users.password],
)
val defaultDateTime = DateTime.parse("2024-11-13T23:58:41.15", DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SS"))
fun mapToDailyGoal(it: ResultRow) = DailyGoal(
    id = it[DailyGoals.id],
    userId = it[DailyGoals.userId],
    goalName = it[DailyGoals.goalName],
    goalDescription = it[DailyGoals.goalDescription],
//    createdAt = it[DailyGoals.createdAt]?:defaultDateTime,
    isCompleted = it[DailyGoals.isCompleted],
//    date = it[DailyGoals.date]?: defaultDateTime,
    priority = it[DailyGoals.priority],
    notes = it[DailyGoals.notes]
)
