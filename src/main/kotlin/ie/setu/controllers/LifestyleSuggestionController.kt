package ie.setu.controllers
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.DailyHabit
import ie.setu.domain.repository.DailyHabitsDAO
import ie.setu.domain.repository.LifestyleSuggestionDAO
import io.javalin.http.Context
import org.joda.time.DateTime
import java.sql.SQLException


object LifestyleSuggestionController {

//    suggesting a life style suggestion to the user based on the dailyhabits of the user based upon the
//    last 7 days' data , for eg if the user has sleep less than 40 over 7 days the function would recomment the
//    user to get better sleep

    fun generateLifeStyleSuggestions(ctx: Context) {

        try {
            val mapper = jacksonObjectMapper()
            var  userId = ctx.pathParam("userId").toIntOrNull()
            val lifeStyleSuggesterDao = LifestyleSuggestionDAO()
            val suggestions = mutableListOf<String>()
            if (userId == null) {
                ctx.status(400).json(mapOf("error" to "Invalid user id"))
                return
            }
            val allDailyHabits = lifeStyleSuggesterDao.getlifestyleSuggestions(userId)

            if (allDailyHabits.size < 7) {
                suggestions.add("Not enough data to provide suggestions. Please record more habits for atleast a week.")
                ctx.json(mapOf("suggestions" to suggestions))
                return

            }


            val sumOfHabits = calculateSumOfHabits(allDailyHabits)
            if (sumOfHabits.hoursSlept < 40) {
                suggestions.add("You should get more sleep (less than 6 hours on average per day).")
            }

            if (sumOfHabits.alcoholIntakeMl > 200) {
                suggestions.add("You should reduce alcohol intake ( avg of more than 200ml per day).")
            }

            if (sumOfHabits.caffeineIntakeMg > 2500) {
                suggestions.add("You should watch your caffeine intake as it is over 2500mg in a week.")
            }

            if (sumOfHabits.screenTimeMinutes > 900) {
                suggestions.add("You should try to reduce your screen time usage (more than 15 hours per week).")
            }

            if (sumOfHabits.stepsWalked < 70000) {
                suggestions.add("You should get more steps in (avg of less than 7000 steps per day).")
            }

            if (sumOfHabits.carbsIntakeG > 2000) {
                suggestions.add("You should watch your carbs intake (currently your intake is more than 2000 grams weekly).")
            }

            if (sumOfHabits.carbsIntakeG < 1800) {
                suggestions.add("You should have more carbs in your diet.")
            }

            if (sumOfHabits.proteinIntakeG < 350) {
                suggestions.add("You should add more protein to your diet.")
            }

            if (sumOfHabits.totalTimeExercised < 150) {
                suggestions.add("You should exercise more as your weekly time spent on exercise is low.")
            }

            ctx.json(mapOf("suggestions" to suggestions))
        }catch (e: SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e: Exception){

            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }



    }
}

//calculating the sum of habit values over the period of 7 days

fun calculateSumOfHabits(dailyHabits: List<DailyHabit>): DailyHabit {
    val sumHoursSlept = dailyHabits.sumOf { it.hoursSlept }
    val sumStepsWalked = dailyHabits.sumOf { it.stepsWalked }
    val sumCaloriesBurned = dailyHabits.sumOf { it.caloriesBurned }
    val sumTotalTimeExercised = dailyHabits.sumOf { it.totalTimeExercised }
    val sumWaterIntakeMl = dailyHabits.sumOf { it.waterIntakeMl }
    val sumCalorieIntake = dailyHabits.sumOf { it.calorieIntake }
    val sumProteinIntakeG = dailyHabits.sumOf { it.proteinIntakeG }
    val sumCarbsIntakeG = dailyHabits.sumOf { it.carbsIntakeG }
    val sumScreenTimeMinutes = dailyHabits.sumOf { it.screenTimeMinutes }
    val sumCaffeineIntakeMg = dailyHabits.sumOf { it.caffeineIntakeMg }
    val sumAlcoholIntakeMl = dailyHabits.sumOf { it.alcoholIntakeMl }

    return DailyHabit(
        id = 0,
        date = DateTime.now(),
        userId = dailyHabits.firstOrNull()?.userId ?: 0,
        hoursSlept = sumHoursSlept,
        stepsWalked = sumStepsWalked,
        caloriesBurned = sumCaloriesBurned,
        totalTimeExercised = sumTotalTimeExercised,
        waterIntakeMl = sumWaterIntakeMl,
        calorieIntake = sumCalorieIntake,
        proteinIntakeG = sumProteinIntakeG,
        carbsIntakeG = sumCarbsIntakeG,
        screenTimeMinutes = sumScreenTimeMinutes,
        caffeineIntakeMg = sumCaffeineIntakeMg,
        alcoholIntakeMl = sumAlcoholIntakeMl,
        notes = ""
    )
}