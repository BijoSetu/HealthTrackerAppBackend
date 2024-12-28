package ie.setu.controllers


import ie.setu.controllers.controllerComponents.sendResponse
import ie.setu.controllers.controllerComponents.validateUserId
import ie.setu.controllers.controllerComponents.validateUserIdAndId
import ie.setu.domain.UserAttribute
import ie.setu.domain.repository.UserAttributesDao
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import java.sql.SQLException

object UserAttributesController {

    fun addUserAttributes(ctx: Context) {
        try {
            val userAttributesDao = UserAttributesDao()

            // Get userId from path parameter
            val userId = ctx.pathParam("userId").toIntOrNull()
            if (!validateUserId(ctx, userId)) return

            // Map the request body to a UserAttribute object and add the userId
            val userAttribute = jsonToObject<UserAttribute>(ctx.body()).copy(userId = userId!!)

            // Call the DAO method to add the user attributes
            userAttributesDao.addUserAttribute(userAttribute)

            ctx.status(200).json(mapOf("success" to "User attributes added"))
        } catch (e: SQLException) {
            ctx.status(400).json(mapOf("SQLError" to e.message.toString()))
        } catch (e: Exception) {
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }
    }

    fun updateUserAttributes(ctx: Context) {
        try {
            val userAttributesDao = UserAttributesDao()

            // Get userId and attributeId from path parameters
            val userId = ctx.pathParam("userId").toIntOrNull()


            if (!validateUserId(ctx, userId)) return

            // Map the request body to a UserAttribute object
            val updatedUserAttributes = jsonToObject<UserAttribute>(ctx.body()).copy(userId = userId!!)

            // Call the DAO method to update the user attributes
            val rowsUpdated = userAttributesDao.updateUserAttribute(userId, updatedUserAttributes)

            sendResponse(ctx, rowsUpdated, "updated", "updating")
        } catch (e: SQLException) {
            ctx.status(400).json(mapOf("SQLError" to e.message.toString()))
        } catch (e: Exception) {
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }
    }
    fun deleteUserAttributes(ctx: Context) {
        try {
            val userAttributesDao = UserAttributesDao()

            // Get userId from path parameter
            val userId = ctx.pathParam("userId").toIntOrNull()
            if (!validateUserId(ctx, userId)) return

            // Call the DAO method to delete the user attributes
            val deletedRows = userAttributesDao.deleteUserAttribute(userId!!)

            sendResponse(ctx, deletedRows, "deleted", "deleting")
        } catch (e: SQLException) {
            ctx.status(400).json(mapOf("SQLError" to e.message.toString()))
        } catch (e: Exception) {
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }
    }


    fun getUserAttributes(ctx: Context) {
        try {
            val userAttributesDao = UserAttributesDao()

            // Get userId from path parameter
            val userId = ctx.pathParam("userId").toIntOrNull()
            if ( !validateUserId(ctx, userId)) return

            // Call the DAO method to get the user attributes
            val userAttributes = userAttributesDao.getUserAttributes(userId!!)

            if (userAttributes != null) {
                ctx.status(200).json(mapOf("success" to true, "userAttributes" to userAttributes))
            } else {
                ctx.status(400).json(mapOf("success" to false, "message" to "User attributes not found"))
            }
        } catch (e: SQLException) {
            ctx.status(400).json(mapOf("SQLError" to e.message.toString()))
        } catch (e: Exception) {
            ctx.status(400).json(mapOf("error" to e.message.toString()))
        }
    }

}