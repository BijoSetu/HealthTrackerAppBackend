package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.DailyGoal
import ie.setu.domain.DailyHabit
import ie.setu.domain.db.DailyHabits
import ie.setu.domain.repository.DailyGoalsDAO
import ie.setu.domain.repository.DailyHabitsDAO
import io.javalin.http.Context

object DailyHabitsController {

    fun addDailyHabitsToUser(ctx: Context) {

        val mapper = jacksonObjectMapper()
        var  userId = ctx.pathParam("user-id").toIntOrNull()
        val dailyHabitsDao = DailyHabitsDAO()

        if (userId == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user id"))
            return
        }
        val dailyHabit = mapper.readValue<DailyHabit>(ctx.body()).copy(userId = userId)
        dailyHabitsDao.addDailyHabits(dailyHabit)
        ctx.status(200).json(mapOf("success" to "true" , "message" to "daily Habit added"))
    }

    fun deleteDailyHabitLog(ctx: Context) {
         val userId = ctx.pathParam("user-id").toIntOrNull()
        val id = ctx.pathParam("id").toIntOrNull()
val dailyHabitsDao = DailyHabitsDAO()
        if (userId == null || id == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user id"))
            return
        }

        val deleted = dailyHabitsDao.deleteDailyHabit(id, userId)
        if (deleted == 1) {
            ctx.status(200).json(mapOf("success" to "true", "message" to "Daily Habit log  is  deleted"))
        } else {
            ctx.status(404).json(mapOf("error" to "Daily Habit does not exist"))
        }
    }

    fun getAllDailyHabit(ctx: Context) {
        val userId = ctx.pathParam("user-id").toInt()
        val dailyHabitsDao = DailyHabitsDAO()

        val dailyHabits = dailyHabitsDao.getAllDailyHabitsByUserId(userId)
        ctx.json(dailyHabits)
    }

    fun updateDailyHabit(ctx: Context){


        val userId = ctx.pathParam("user-id").toIntOrNull()
        val id = ctx.pathParam("id").toIntOrNull()

        if(userId ==null || id == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user id"))
            return
        }
        val mapper = jacksonObjectMapper()
        val dailyHabitDao = DailyHabitsDAO()
        val updatedDailyHabit = mapper.readValue<DailyHabit>(ctx.body()).copy(userId = userId, id = id)

        val updatedHabit = dailyHabitDao.updateDailyHabit(id,userId,updatedDailyHabit)
        if (updatedHabit == 1) {
            ctx.status(200).json(mapOf("success" to "true", "message" to "Daily goal updated"))
        } else {
            ctx.status(404).json(mapOf("error" to "Daily goal not found"))
        }
    }
}