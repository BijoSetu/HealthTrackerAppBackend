package ie.setu.utils
import ie.setu.domain.db.Users
import ie.setu.domain.User
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    user_id = it[Users.user_id],
    name = it[Users.name],
    email = it[Users.email],password = it[Users.password],

)

//fun mapToDailyGoals(it: ResultRow) =