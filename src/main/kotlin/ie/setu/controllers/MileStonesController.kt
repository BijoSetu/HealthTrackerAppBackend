package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.controllers.controllerComponents.sendResponse
import ie.setu.controllers.controllerComponents.validateUserId
import ie.setu.controllers.controllerComponents.validateUserIdAndId
import ie.setu.domain.Milestone
import ie.setu.domain.repository.MileStonesDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import org.joda.time.DateTime
import java.sql.SQLException

object MileStonesController {

    fun addMileStonesToUser(ctx: Context) {
//adding a milestone for the user ,eg: "completed a half marathon"

        try {

            val mileStoneDao = MileStonesDAO()

            val userId = ctx.pathParam("userId").toIntOrNull()
            if (!validateUserId(ctx, userId))return
//            copy the user id to the object from request body
            val mileStone = jsonToObject<Milestone>(ctx.body()).copy(userId = userId!!)

             mileStoneDao.addNewMileStone(mileStone)
            ctx.status(200).json(mapOf("success" to "MileStone added"))
        } catch (e: SQLException) {
            ctx.status(400).json(mapOf("SQLError" to e.message.toString()))
        }catch (e:Exception){

            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }
    }

//    updating milestone for a user
    fun updateMileStoneOfUser(ctx: Context) {

        try {
            val mileStoneDao = MileStonesDAO()

            val userId = ctx.pathParam("userId").toIntOrNull()
            val id = ctx.pathParam("id").toIntOrNull()

           if(!validateUserIdAndId(ctx, userId,id))return
//            copy the id and user id from the path parameter to the object in request body
            val updatedDailyObject = jsonToObject<Milestone>(ctx.body()).copy(id=id!!, userId = userId!!)

            val rowsUpdated = mileStoneDao.updateMilestone(id,userId,updatedDailyObject)
            ctx.status(200).json(mapOf("success" to "Successfully updated"))
        }catch (e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }
//deleting a milestone
    fun deleteMileStoneOfUser(ctx: Context) {

        try {

            val mileStoneDao = MileStonesDAO()
            val userId = ctx.pathParam("userId").toIntOrNull()
            val id = ctx.pathParam("id").toIntOrNull()
           if(!validateUserIdAndId(ctx, userId,id))return
            val deletedRows= mileStoneDao.deleteMilestone(userId!!,id!!)
            ctx.status(200).json(mapOf("success" to "MileStoneDeleted"))
        }catch (e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }

//get all the milestones of a user
    fun getAllMilesStonesOfUser(ctx: Context) {


        try {

            val milestoneDao = MileStonesDAO()
            var  userId = ctx.pathParam("userId").toIntOrNull()
            if(!validateUserId( ctx, userId))return
            val mileStones = milestoneDao.getMilestonesByUserId(userId!!)
         if(mileStones.isNotEmpty()){
             ctx.status(200).json(mapOf("success" to true,"milestones" to mileStones))
         }else{
             ctx.status(400).json(mapOf("success" to false,"milestones" to "no milestones available"))
         }
        }catch (e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }
}
