package ie.setu.domain

import org.joda.time.DateTime


data class DailyHabit(


    val id: Int,
    val userId: Int,
    val date: DateTime?,
    val hoursSlept: Double,
    val stepsWalked: Double,
    val caloriesBurned: Double,
    val totalTimeExercised: Double,
    val waterIntakeMl: Double,
    val calorieIntake: Double,
    val proteinIntakeG: Double,
    val carbsIntakeG: Double,
    val screenTimeMinutes: Double,
    val caffeineIntakeMg: Double,
    val alcoholIntakeMl: Double,
    val notes: String?
)