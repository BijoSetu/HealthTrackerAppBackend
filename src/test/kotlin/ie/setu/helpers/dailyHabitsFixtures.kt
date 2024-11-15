package ie.setu.helpers

import ie.setu.domain.DailyHabit
import org.joda.time.DateTime

val dailyHabits = arrayListOf(
    DailyHabit(
        id=1,
        date= DateTime.now(),
        userId = 1,
        hoursSlept = 7.5,
        stepsWalked = 10000.0,
        caloriesBurned = 500.0,
        totalTimeExercised = 60.0,
        waterIntakeMl = 2000.0,
        calorieIntake = 1800.0,
        proteinIntakeG = 120.0,
        carbsIntakeG = 200.0,
        screenTimeMinutes = 180.0,
        caffeineIntakeMg = 150.0,
        alcoholIntakeMl = 0.0,
        notes = "Good day with a balanced routine."
    ),
    DailyHabit(
        id=1,
        date= DateTime.now(),
        userId = 2,
        hoursSlept = 6.0,
        stepsWalked = 8000.0,
        caloriesBurned = 400.0,
        totalTimeExercised = 45.0,
        waterIntakeMl = 1500.0,
        calorieIntake = 2000.0,
        proteinIntakeG = 100.0,
        carbsIntakeG = 250.0,
        screenTimeMinutes = 300.0,
        caffeineIntakeMg = 200.0,
        alcoholIntakeMl = 50.0,
        notes = "Needed more sleep; screen time was high."
    ),
    DailyHabit(
        id=1,
        date= DateTime.now(),
        userId = 3,
        hoursSlept = 8.0,
        stepsWalked = 12000.0,
        caloriesBurned = 600.0,
        totalTimeExercised = 75.0,
        waterIntakeMl = 2500.0,
        calorieIntake = 1700.0,
        proteinIntakeG = 140.0,
        carbsIntakeG = 180.0,
        screenTimeMinutes = 120.0,
        caffeineIntakeMg = 100.0,
        alcoholIntakeMl = 0.0,
        notes = "Felt great today; productive and healthy."
    )
)
