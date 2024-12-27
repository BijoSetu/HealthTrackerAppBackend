package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.controllers.controllerComponents.sendResponse
import ie.setu.controllers.controllerComponents.validateUserId
import ie.setu.controllers.controllerComponents.validateUserIdAndId
import ie.setu.domain.Milestone
import ie.setu.domain.repository.MileStonesDAO
import io.javalin.http.Context
import org.joda.time.DateTime
import java.sql.SQLException

object MileStonesController {

    fun addMileStonesToUser(ctx: Context) {
//adding a milestone for the user ,eg: "completed a half marathon"

        try {

            val mileStoneDao = MileStonesDAO()
            val mapper = jacksonObjectMapper()
            val userId = ctx.pathParam("userId").toIntOrNull()
            if (!validateUserId(ctx, userId))return
            val requestBody = ctx.body()
            println("Received JSON: $requestBody")
            println(DateTime.now())
            val mileStone = mapper.readValue<Milestone>(ctx.body()).copy(userId = userId!!)

             mileStoneDao.addNewMileStone(mileStone)
            ctx.json(mapOf("success" to "MileStone added"))
        } catch (e: SQLException) {
            ctx.json(mapOf("SQLError" to e.message.toString()))
        }catch (e:Exception){

            ctx.json(mapOf("error" to e.message.toString()))
        }
    }

//    updating milestone for a user
    fun updateMileStoneOfUser(ctx: Context) {

        try {
            val mileStoneDao = MileStonesDAO()
            val mapper = jacksonObjectMapper()
            var  userId = ctx.pathParam("userId").toIntOrNull()
            var  id = ctx.pathParam("id").toIntOrNull()

           if(!validateUserIdAndId(ctx, userId,id))return

            val updatedDailyObject = mapper.readValue<Milestone>(ctx.body()).copy(id=id!!, userId = userId!!)

            val rowsUpdated = mileStoneDao.updateMilestone(id,userId,updatedDailyObject)
            sendResponse(ctx,rowsUpdated,"updated","updating")
        }catch (e:SQLException){
            ctx.json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.json(mapOf("error" to e.message.toString()))
        }

    }
//deleting a milestone
    fun deleteMileStoneOfUser(ctx: Context) {


        try {
            val mapper = jacksonObjectMapper()
            val mileStoneDao = MileStonesDAO()
            var  userId = ctx.pathParam("userId").toIntOrNull()
            val id = ctx.pathParam("id").toIntOrNull()
           if(!validateUserIdAndId(ctx, userId,id))return
            val deletedRows= mileStoneDao.deleteMilestone(userId!!,id!!)
            sendResponse(ctx,deletedRows,"deleted","deleting")
        }catch (e:SQLException){
            ctx.json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.json(mapOf("error" to e.message.toString()))
        }

    }

//get all the milestones of a user
    fun getAllMilesStonesOfUser(ctx: Context) {


        try {
            val mapper = jacksonObjectMapper()
            val milestoneDao = MileStonesDAO()
            var  userId = ctx.pathParam("userId").toIntOrNull()
            if(!validateUserId( ctx, userId))return
            val mileStones = milestoneDao.getMilestonesByUserId(userId!!)
         if(mileStones.isNotEmpty()){
             ctx.json(mapOf("success" to true,"milestones" to mileStones))
         }else{
             ctx.json(mapOf("success" to false,"milestones" to "no milestones available"))
         }
        }catch (e:SQLException){
            ctx.json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.json(mapOf("error" to e.message.toString()))
        }

    }
}
