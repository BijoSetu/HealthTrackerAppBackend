package ie.setu.domain.repository

import ie.setu.domain.db.DailyGoals.goalName
import ie.setu.domain.DailyGoal
import ie.setu.domain.db.DailyGoals
import ie.setu.domain.db.Users
import ie.setu.utils.mapToDailyGoal
import ie.setu.utils.mapToUser
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DailyGoalsDAO {

   fun addDailyGoals ( dailyGoals : DailyGoal) {
       transaction {
           DailyGoals.insert {
               it[userId] = dailyGoals.userId
               it[ goalName] = dailyGoals. goalName
               it[goalDescription] = dailyGoals.goalDescription
               it[isCompleted] = dailyGoals.isCompleted
               it[priority] = dailyGoals.priority
               it[ createdAt] = dailyGoals.createdAt
               it[date] = dailyGoals.date
               it[notes ] = dailyGoals.notes
           }

       }


   }

    fun deleteDailyGoal(id:Int,userId: Int):Int{

     return transaction {
         DailyGoals.deleteWhere{ DailyGoals.id eq id and (DailyGoals.userId eq userId) }
        }


    }
    fun getAllDailyGoalsByUserId(userId: Int): List<DailyGoal> {
        return transaction {
            DailyGoals.selectAll().where { DailyGoals.userId eq userId }
                .map { mapToDailyGoal(it) }
        }
    }

    fun updateDailyGoal(id: Int, userId: Int, updatedGoal: DailyGoal): Int {
        return transaction {
            DailyGoals.update({ (DailyGoals.id eq id) and (DailyGoals.userId eq userId) }) {
                it[goalName] = updatedGoal.goalName
                it[goalDescription] = updatedGoal.goalDescription
                it[isCompleted] = updatedGoal.isCompleted
                it[priority] = updatedGoal.priority
                it[date] = updatedGoal.date
                it[notes] = updatedGoal.notes
            }
        }
    }



}