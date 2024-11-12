package ie.setu.controllers


import ie.setu.domain.repository.UserDao
import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.PayloadLogin
import ie.setu.domain.User


object UserController {

    private val userDao = UserDao()

    fun getAllUsers(ctx: Context) {
        ctx.json(userDao.getAll())
    }

//    logging in a user if already exists
fun loginUser(ctx: Context):Boolean {
    val mapper = jacksonObjectMapper()
    val userDetails = mapper.readValue<PayloadLogin>(ctx.body())
    val userExists = userDao.loginUser(userDetails)
    return if (userExists) {
        ctx.status(200).json(mapOf("success" to true))
        true
    } else {
        ctx.status(400).json(mapOf("success" to false, "error" to "User not found"))
        false
    }
}


    fun getUserById(ctx: Context) {
        val user = userDao.getUserById(ctx.pathParam("user-id").toInt())
        if (user != null) {
            ctx.json(user)
        }
    }
//    registering a new user
    fun registerNewUser(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<User>(ctx.body())
        userDao.registerNewUser(user)
        ctx.json(user)
        ctx.status(201)
    }


    fun deleteUser(ctx: Context) {
        val userId = ctx.pathParam("user-id").toIntOrNull()

        if(userId == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user id"))
            return
        }
        val deletedId= userDao.deleteUser(userId)

        if(deletedId!= 0 ){ctx.status(200).json(mapOf("success" to true)
        )
        }else{ctx.status(400).json(mapOf("error" to "User not found"))}
    }
    fun updateUser(ctx: Context) {
        val userId = ctx.pathParam("user-id").toIntOrNull()
        val mapper = jacksonObjectMapper()
        val user = mapper.readValue<PayloadLogin>(ctx.body())

        if(userId == null) {
            ctx.status(400).json(mapOf("error" to "Invalid user id"))
            return
        }
      val deletedRows =    userDao.updateUser(userId,user)
        if(deletedRows != 0){

            ctx.status(200).json(mapOf("success" to true))
        }else{ctx.status(400).json(mapOf("success" to false,"error" to "User not found"))}


    }
}
