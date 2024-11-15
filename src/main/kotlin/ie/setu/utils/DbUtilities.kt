package ie.setu.utils
import ie.setu.domain.DailyGoal
import ie.setu.domain.DailyHabit
import ie.setu.domain.db.Users
import ie.setu.domain.User
import ie.setu.domain.db.DailyGoals
import ie.setu.domain.db.DailyHabits
import org.jetbrains.exposed.sql.ResultRow
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun mapToUser(it: ResultRow) = User(
    user_id = it[Users.user_id],
    name = it[Users.name],
    email = it[Users.email],password = it[Users.password],
)

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


fun mapToDailyHabits(it: ResultRow) = DailyHabit(

    id = it[DailyHabits.id],
    userId = it[DailyHabits.userId],
    date = it[DailyHabits.date],
    caffeineIntakeMg = it[DailyHabits.caffeineIntakeMg],
    proteinIntakeG = it[DailyHabits.proteinIntakeG],
    notes = it[DailyHabits.notes],
    screenTimeMinutes = it[DailyHabits.screenTimeMinutes],
    totalTimeExercised = it[DailyHabits.totalTimeExercised],
    caloriesBurned = it[DailyHabits.caloriesBurned],
    carbsIntakeG = it[DailyHabits.carbsIntakeG],
    hoursSlept = it[DailyHabits.hoursSlept],
    calorieIntake = it[DailyHabits.calorieIntake],
    waterIntakeMl = it[DailyHabits.waterIntakeMl],
    stepsWalked = it[DailyHabits.stepsWalked],
    alcoholIntakeMl = it[DailyHabits.alcoholIntakeMl],
)

