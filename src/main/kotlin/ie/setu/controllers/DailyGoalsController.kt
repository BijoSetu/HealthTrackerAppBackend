package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.controllers.controllerComponents.sendResponse
import ie.setu.controllers.controllerComponents.validateUserId
import ie.setu.controllers.controllerComponents.validateUserIdAndId

import ie.setu.domain.DailyGoal
import ie.setu.domain.User
import ie.setu.domain.db.DailyGoals
import ie.setu.domain.repository.DailyGoalsDAO
import ie.setu.domain.repository.UserDao
import io.javalin.http.Context
import java.sql.SQLException

object DailyGoalsController {

private val dailyGoalsDao = DailyGoalsDAO()

//    adding a daily goal for a user
    fun addDailyGoalsToUser(ctx: Context) {

        try {
            val mapper = jacksonObjectMapper()
            var  userId = ctx.pathParam("userId").toIntOrNull()

            if(!validateUserId(ctx, userId))return
            val dailyGoals = mapper.readValue<DailyGoal>(ctx.body()).copy(userId = userId!!)
            dailyGoalsDao.addDailyGoals(dailyGoals)
            ctx.status(200).json(mapOf("success" to "true" , "message" to "daily goal added"))
        }catch(e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e: Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }
//deleting a daily goal of a user
    fun deleteDailyGoalsFromUser(ctx: Context) {

        try {
            val id = ctx.pathParam("goalId").toIntOrNull()
            val userId = ctx.pathParam("userId").toIntOrNull()

            if(!validateUserIdAndId(ctx, userId, id)) return

            val deleted = dailyGoalsDao.deleteDailyGoal(id!!, userId!!)
            sendResponse(ctx,deleted,"deleted","deleting")
        }catch(e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch(e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }
//getting all the daily goals from a user
    fun getAllDailyGoalsByUserId(ctx: Context) {

        try {
            val userId = ctx.pathParam("userId").toInt()

            val dailyGoals = dailyGoalsDao.getAllDailyGoalsByUserId(userId)

            ctx.json(dailyGoals)
        }catch (e: SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }
//updating a daily goal from a user
    fun updateDailyGoalsForUser(ctx: Context) {


        try {
            val mapper = jacksonObjectMapper()
            val id = ctx.pathParam("goal-id").toIntOrNull()
            val userId = ctx.pathParam("user-id").toIntOrNull()

            if(!validateUserIdAndId(ctx, userId, id))return

            val updatedDailyGoal = mapper.readValue<DailyGoal>(ctx.body()).copy(userId = userId!!, id = id!!)

            val updated = dailyGoalsDao.updateDailyGoal(id,userId,updatedDailyGoal)
            sendResponse(ctx,updated,"updated","updating")
        }catch(e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }


    }

}