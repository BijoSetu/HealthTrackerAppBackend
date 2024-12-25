package ie.setu.controllers
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.controllers.controllerComponents.validateUserId
import ie.setu.controllers.controllerComponents.validateUserIdAndId
import ie.setu.domain.DailyHabit
import ie.setu.domain.repository.DailyHabitsDAO
import io.javalin.http.Context
import java.sql.SQLException

object DailyHabitsController {

//    adding a daily habit , like hours slept , steps walked etc
    fun addDailyHabitsToUser(ctx: Context) {

        try {
            val mapper = jacksonObjectMapper()
            val  userId = ctx.pathParam("userId").toIntOrNull()
            val dailyHabitsDao = DailyHabitsDAO()

            if(!validateUserId(ctx,userId ))return
            val dailyHabit = mapper.readValue<DailyHabit>(ctx.body()).copy(userId = userId!!)
            dailyHabitsDao.addDailyHabits(dailyHabit)
            ctx.status(200).json(mapOf("success" to "true" , "message" to "daily Habit added"))
        }
      catch (e: SQLException){
          ctx.status(400).json(mapOf("error" to e.message.toString()))
      }catch (e:Exception){
          ctx.status(400).json(mapOf("error" to e.message.toString()))
      }
    }
//delete a  daily habit from the db
    fun deleteDailyHabitLog(ctx: Context) {

        try {
            val userId = ctx.pathParam("userId").toIntOrNull()
            val id = ctx.pathParam("id").toIntOrNull()
            val dailyHabitsDao = DailyHabitsDAO()
            if (!validateUserIdAndId(ctx,userId,id ))  return


            val deleted = dailyHabitsDao.deleteDailyHabit(id!!, userId!!)
            if (deleted == 1) {
                ctx.status(200).json(mapOf("success" to "true", "message" to "Daily Habit log  is  deleted"))
            } else {
                ctx.status(404).json(mapOf("error" to "Daily Habit does not exist"))
            }
        }catch (e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }
//get all the daily habit input by the user
    fun getAllDailyHabit(ctx: Context) {
        try {
            val userId = ctx.pathParam("userId").toInt()
            val dailyHabitsDao = DailyHabitsDAO()
            if(!validateUserId(ctx,userId ))return
            val dailyHabits = dailyHabitsDao.getAllDailyHabitsByUserId(userId)
            ctx.json(dailyHabits)
        }catch (e:SQLException){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }catch (e:Exception){
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }

    }
//update daily habit of the user
    fun updateDailyHabit(ctx: Context){

try {
    val userId = ctx.pathParam("userId").toIntOrNull()
    val id = ctx.pathParam("id").toIntOrNull()

    if (!validateUserId(ctx,userId)) return
    val mapper = jacksonObjectMapper()
    val dailyHabitDao = DailyHabitsDAO()
    val updatedDailyHabit = mapper.readValue<DailyHabit>(ctx.body()).copy(userId = userId!!, id = id!!)

    val updatedHabit = dailyHabitDao.updateDailyHabit(id,userId,updatedDailyHabit)
    if (updatedHabit == 1) {
        ctx.status(200).json(mapOf("success" to "true", "message" to "Daily goal updated"))
    } else {
        ctx.status(404).json(mapOf("error" to "Daily goal not found"))
    }

}catch (e:SQLException){
    ctx.status(400).json(mapOf("error" to e.message.toString()))
}catch (e:Exception){
    ctx.status(400).json(mapOf("error" to e.message.toString()))
}
      }
}