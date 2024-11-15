package ie.setu.domain.repository

import ie.setu.domain.DailyGoal
import ie.setu.domain.DailyHabit
import ie.setu.domain.db.DailyGoals
import ie.setu.domain.db.DailyHabits
import ie.setu.utils.mapToDailyHabits
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class DailyHabitsDAO {

    fun addDailyHabits ( dailyHabit : DailyHabit) {
        transaction {
            DailyHabits.insert {
                it[userId] = dailyHabit.userId
                it[date] = dailyHabit.date?: DateTime.now()
                it[hoursSlept] = dailyHabit.hoursSlept
                it[stepsWalked] = dailyHabit.stepsWalked
                it[caloriesBurned] = dailyHabit.caloriesBurned
                it[totalTimeExercised] = dailyHabit.totalTimeExercised
                it[waterIntakeMl] = dailyHabit.waterIntakeMl
                it[calorieIntake] = dailyHabit.calorieIntake
                it[proteinIntakeG] = dailyHabit.proteinIntakeG
                it[carbsIntakeG] = dailyHabit.carbsIntakeG
                it[screenTimeMinutes] = dailyHabit.screenTimeMinutes
                it[caffeineIntakeMg] = dailyHabit.caffeineIntakeMg
                it[alcoholIntakeMl] = dailyHabit.alcoholIntakeMl
                it[notes] = dailyHabit.notes
            }
}}

    fun deleteDailyHabit(id:Int,userId: Int):Int{

        return transaction {
            DailyHabits.deleteWhere{ DailyHabits.id eq id and (DailyHabits.userId eq userId) }
        }


    }

    fun getAllDailyHabitsByUserId(userId: Int): List<DailyHabit> {
        return transaction {
            DailyHabits.selectAll().where { DailyHabits.userId eq userId }
                .map { mapToDailyHabits(it) }
        }
    }

    fun updateDailyHabit(id:Int, userId:Int, dailyHabit:DailyHabit):Int {

        return transaction {

            DailyHabits.update({ (DailyHabits.id eq id) and (DailyHabits.userId eq userId) }) {
                it[date] = dailyHabit.date?:DateTime.now()
                it[hoursSlept] = dailyHabit.hoursSlept
                it[stepsWalked] = dailyHabit.stepsWalked
                it[caloriesBurned] = dailyHabit.caloriesBurned
                it[totalTimeExercised] = dailyHabit.totalTimeExercised
                it[waterIntakeMl] = dailyHabit.waterIntakeMl
                it[calorieIntake] = dailyHabit.calorieIntake
                it[proteinIntakeG] = dailyHabit.proteinIntakeG
                it[carbsIntakeG] = dailyHabit.carbsIntakeG
                it[screenTimeMinutes] = dailyHabit.screenTimeMinutes
                it[caffeineIntakeMg] = dailyHabit.caffeineIntakeMg
                it[alcoholIntakeMl] = dailyHabit.alcoholIntakeMl
                it[notes] = dailyHabit.notes

            }
        }
    }


}