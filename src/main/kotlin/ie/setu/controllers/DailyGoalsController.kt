package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

import ie.setu.domain.DailyGoal
import ie.setu.domain.User
import ie.setu.domain.db.DailyGoals
import ie.setu.domain.repository.DailyGoalsDAO
import ie.setu.domain.repository.UserDao
import io.javalin.http.Context

object DailyGoalsController {

private val dailyGoalsDao = DailyGoalsDAO()

//    adding a daily goal for a user
    fun addDailyGoalsToUser(ctx: Context) {

        val mapper = jacksonObjectMapper()
        var  userId = ctx.pathParam("user-id").toIntOrNull()

        if (userId == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user id"))
            return
        }
        val dailyGoals = mapper.readValue<DailyGoal>(ctx.body()).copy(userId = userId)
        dailyGoalsDao.addDailyGoals(dailyGoals)
        ctx.status(200).json(mapOf("success" to "true" , "message" to "daily goal added"))
    }

    fun deleteDailyGoalsFromUser(ctx: Context) {
        val id = ctx.pathParam("goal-id").toIntOrNull()
        val userId = ctx.pathParam("user-id").toIntOrNull()

        if (id == null || userId == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user id or goal id"))
            return
        }

        val deleted = dailyGoalsDao.deleteDailyGoal(id, userId)
        if (deleted == 1) {
            ctx.status(200).json(mapOf("success" to "true", "message" to "Daily goal is  deleted"))
        } else {
            ctx.status(404).json(mapOf("error" to "Daily goal does not exist"))
        }
    }

    fun getAllDailyGoalsByUserId(ctx: Context) {
        val userId = ctx.pathParam("user-id").toInt()

        val dailyGoals = dailyGoalsDao.getAllDailyGoalsByUserId(userId)
        ctx.json(dailyGoals)
    }

    fun updateDailyGoalsForUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val id = ctx.pathParam("goal-id").toIntOrNull()
        val userId = ctx.pathParam("user-id").toIntOrNull()

        if (id == null || userId == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user-id or id of the goal"))
            return
        }

        val updatedDailyGoal = mapper.readValue<DailyGoal>(ctx.body()).copy(userId = userId, id = id)

        val updated = dailyGoalsDao.updateDailyGoal(id,userId,updatedDailyGoal)
        if (updated== 1) {
            ctx.status(200).json(mapOf("success" to "true", "message" to "Daily goal updated"))
        } else {
            ctx.status(404).json(mapOf("error" to "Daily goal not found"))
        }
    }

}