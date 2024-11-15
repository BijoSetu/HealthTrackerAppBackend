package ie.setu.domain.repository

import ie.setu.domain.DailyHabit
import ie.setu.domain.db.DailyHabits
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class LifestyleSuggestionDAO {

    fun getlifestyleSuggestions(userId:Int): List<DailyHabit> {

        val dailyHabits = transaction {
            DailyHabits.selectAll().where { DailyHabits.userId eq userId }
                .orderBy(DailyHabits.date, SortOrder.DESC)
                .limit(7)
                .map {
                    DailyHabit(
                        id = it[DailyHabits.id],
                        date = it[DailyHabits.date],
                        userId = it[DailyHabits.userId],
                        hoursSlept = it[DailyHabits.hoursSlept],
                        stepsWalked = it[DailyHabits.stepsWalked],
                        caloriesBurned = it[DailyHabits.caloriesBurned],
                        totalTimeExercised = it[DailyHabits.totalTimeExercised],
                        waterIntakeMl = it[DailyHabits.waterIntakeMl],
                        calorieIntake = it[DailyHabits.calorieIntake],
                        proteinIntakeG = it[DailyHabits.proteinIntakeG],
                        carbsIntakeG = it[DailyHabits.carbsIntakeG],
                        screenTimeMinutes = it[DailyHabits.screenTimeMinutes],
                        caffeineIntakeMg = it[DailyHabits.caffeineIntakeMg],
                        alcoholIntakeMl = it[DailyHabits.alcoholIntakeMl],
                        notes = it[DailyHabits.notes]
                    )
                }

        }
         return dailyHabits
    }
    }