package ie.setu.controllers

import ie.setu.domain.repository.UserDao
import io.javalin.http.Context
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.PayloadLogin
import ie.setu.domain.PayloadUpdate
import ie.setu.domain.User
import java.sql.SQLException


object UserController {

    private val userDao = UserDao()

//    get all the users
    fun getAllUsers(ctx: Context) {


        try {
            val allUsers = userDao.getAll()

            if (allUsers.isEmpty()) {
                ctx.status(404).json(mapOf("error" to "No users found"))
            } else {
                ctx.status(200).json(allUsers)
            }
        } catch (e: SQLException) {
            ctx.json(mapOf("error" to e.message))
        } catch (e: Exception) {
            ctx.json(mapOf("error" to e.message))
        }

    }

    //    logging in a user if already exists by comparing matching values on password and email on the db
    fun loginUser(ctx: Context): Boolean {

        try {
            val mapper = jacksonObjectMapper()
            val userDetails = mapper.readValue<PayloadLogin>(ctx.body())
            val user = userDao.loginUser(userDetails)
            return if (user !== null) {
                ctx.status(200).json(mapOf("success" to true,"user" to user))
//                ctx.json(user)
                true
            } else {
                ctx.status(400).json(mapOf("success" to false, "error" to "User not found"))
                false
            }
        } catch (e: SQLException) {
            ctx.json(mapOf("error" to e.message.toString()))
        } catch (e: Exception) {
            ctx.json(mapOf("error" to e.message.toString()))
        }

        return false
    }

//get a user by ID
        fun getUserById(ctx: Context) {
            try {
                val user = userDao.getUserById(ctx.pathParam("userId").toInt())
                if (user != null) {
                    ctx.status(200).json(user)
                } else {
                    ctx.status(404).json(mapOf("error" to "User not found"))
                }
            } catch (e: SQLException) {
                ctx.json(mapOf("error" to e.message.toString()))
            } catch (e: Exception) {
                ctx.json(mapOf("error" to e.message.toString()))
            }

        }

        //    registering a new user
        fun registerNewUser(ctx: Context) {

            try {
                val mapper = jacksonObjectMapper()
                val user:User = mapper.readValue<User>(ctx.body())
                val userId = userDao.registerNewUser(user)
                if( userId!= null){
                    user.userid = userId
                    ctx.json(user)
                    ctx.status(201)
                }else{
                    ctx.json(mapOf("error" to "User already registered"))
                    ctx.status(400)
                    }
            } catch (e: SQLException) {
                ctx.json(mapOf("error" to e.message.toString()))
            } catch (e: Exception) {
                ctx.json(mapOf("error" to e.message.toString()))
            }

        }
// delete a user

        fun deleteUser(ctx: Context) {

            try {
                val userId = ctx.pathParam("id").toIntOrNull()

                if (userId == null) {
                    ctx.status(400).json(mapOf("error" to "Invalid user id"))
                    return
                }
                val deletedId = userDao.deleteUser(userId)

                if (deletedId != 0) {
                    ctx.status(200).json(
                        mapOf("success" to true)
                    )
                } else {
                    ctx.status(400).json(mapOf("error" to "User not found"))
                }
            } catch (e: SQLException) {
                ctx.json(mapOf("error" to e.message.toString()))
            } catch (e: Exception) {
                ctx.json(mapOf("error" to e.message.toString()))
            }

        }
//    get a user by their email address
    fun getUserByEmail(ctx: Context) {
        try {
            val email = ctx.pathParam("email")
            if (email == null) {
                ctx.status(400).json(mapOf("error" to "Email parameter is missing"))
                return
            }

            val user = userDao.getUserByEmail(email)
            if (user != null) {
                ctx.status(200).json( user)
            } else {
                ctx.status(404).json(mapOf("error" to "User not found"))
            }
        } catch (e: SQLException) {
            ctx.status(500).json(mapOf("error" to e.message.toString()))
        } catch (e: Exception) {
            ctx.status(500).json(mapOf("error" to e.message.toString()))
        }
    }
//update a user , update option are available for name and email
        fun updateUser(ctx: Context) {

            try {
                val userId = ctx.pathParam("userId").toIntOrNull()
                val mapper = jacksonObjectMapper()
                val user = mapper.readValue<PayloadUpdate>(ctx.body())

                if (userId == null) {
                    ctx.status(400).json(mapOf("error" to "Invalid user id"))
                    return
                }
                val deletedRows = userDao.updateUser(userId, user)
                if (deletedRows != 0) {

                    ctx.status(200).json(mapOf("success" to true))
                } else {
                    ctx.status(400).json(mapOf("success" to false, "error" to "User not found"))
                }
            } catch (e: SQLException) {
                ctx.json(mapOf("error" to e.message.toString()))
            } catch (e: Exception) {
                ctx.json(mapOf("error" to e.message.toString()))
            }


        }}


