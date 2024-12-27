package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime


object DailyHabits : Table("dailyhabits") {
        val id = integer("id").autoIncrement()
        val userId = integer("userid").references(Users.userId)
        val date = datetime("date").nullable()

        val hoursSlept = double("hours_slept")
        val stepsWalked = double("steps_walked")
        val caloriesBurned = double("calories_burned")
        val totalTimeExercised = double("total_time_exercised")
        val waterIntakeMl = double("water_intake_ml")

        val calorieIntake = double("calorie_intake")
        val proteinIntakeG = double("protein_intake_g")
        val carbsIntakeG = double("carbs_intake_g")


        val screenTimeMinutes = double("screen_time_minutes")
        val caffeineIntakeMg = double("caffeine_intake_mg")
        val alcoholIntakeMl = double("alcohol_intake_ml")

        val notes = text("notes").nullable()
    }
