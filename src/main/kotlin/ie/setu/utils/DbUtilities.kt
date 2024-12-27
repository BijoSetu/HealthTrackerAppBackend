package ie.setu.utils
import ie.setu.domain.DailyGoal
import ie.setu.domain.DailyHabit
import ie.setu.domain.Milestone
import ie.setu.domain.User
import ie.setu.domain.db.*
import org.jetbrains.exposed.sql.ResultRow
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun mapToUser(it: ResultRow) = User(
    userid = it[Users.userId],
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

fun mapToMileStones(it: ResultRow) = Milestone(

    id= it[MileStones.id],
    userId = it[MileStones.userId],
    milestoneName = it[MileStones.milestoneName],
    notes = it[MileStones.notes],
    created = it[MileStones.createdAt],
    updated = it[MileStones.updatedAt],
    achievedDate = it[MileStones.achievedDate]
)
