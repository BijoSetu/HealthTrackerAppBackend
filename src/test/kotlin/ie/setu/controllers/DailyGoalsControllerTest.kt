package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.DailyGoal
import ie.setu.domain.User
import ie.setu.domain.db.DailyGoals
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import kong.unirest.core.HttpResponse
import kong.unirest.core.JsonNode
import kong.unirest.core.Unirest
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

val dailyGoals1 = dailyGoalsTestData[0]
val dailyGoals2 = dailyGoalsTestData[1]
val dailyGoals3 = dailyGoalsTestData[2]

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DailyGoalsControllerTest {
    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()



    @Nested
    inner class ReadDailyGoals {
        @Test
        fun `Getting daily goals for an existing userid returns 200`() {
            // Arrange - Add a user and their daily goals
            val userId = dailyGoals1.userId

            // Act - Call the API to get all daily goals for the user
            val response = getAllDailyGoals(userId)

            // Assert - Check the response status and content
            assertEquals(200, response.status)
            val dailyGoals = jsonToObject<List<DailyGoal>>(response.body)
            assertTrue(dailyGoals.isNotEmpty())
            assertEquals(userId, dailyGoals.first().userId)
        }
        @Test
        fun `Getting daily goals for a non-existing userid returns 404`() {
            // Act - Call the API with a non-existent user ID
            val nonExistentUserId = 9999
            val response = getAllDailyGoals(nonExistentUserId)

            // Assert - Check the response status
            assertEquals(404, response.status)
        }
        @Test
        fun `Getting daily goals with invalid userid format returns 400`() {
            // Act - Call the API with an invalid user ID format
            val invalidUserId = "invalidId"
            val response = Unirest.get(origin + "/api/users/$invalidUserId/daily-goals").asString()

            // Assert - Check the response status
            assertEquals(400, response.status)
        }

    }
    @Nested
    inner class createDailyGoals{

//        @Test

//        fun `Adding a daily goal for an existing user returns 200`() {
//            // Arrange - Add a test user
//            val userId = dailyGoals1.userId
//            val dailyGoal = dailyGoals1
//
//            // Act - Call the API to add a daily goal
//            val response = addDailyGoals(userId, dailyGoal)
//
//            // Assert - Check the response status and content
//            assertEquals(200, response.status)
//            val responseBody = jsonToObject<Map<String, String>>(response.body.toString())
//            assertEquals("true", responseBody["success"])
//            assertEquals("daily goal added", responseBody["message"])
//
//            // Verify - Retrieve the daily goal and validate
//            val dailyGoalsResponse = getAllDailyGoals(userId)
//            val dailyGoalsList = jsonToObject<List<DailyGoal>>(dailyGoalsResponse.body)
//            assertTrue(dailyGoalsList.any { it.goalName == dailyGoal.goalName && it.id == dailyGoal.id })
//
//            // Clean up - Delete the user and their daily goals
//            deleteDailyGoals(userId,dailyGoal.id!!)
//            assertEquals(200, response.status)
//        }
    }








//    helper function to add a daily goal to a user
    private fun addDailyGoals (userId:Int,dailyGoals1:DailyGoal): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users/${userId}/daily-goals")
            .body(dailyGoals1)
            .asJson()
    }

    //helper function to delete a dailygoal from database
    private fun deleteDailyGoals (id: Int,userId: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/${userId}/daily-goals/${id}").asString()
    }

    //helper function to get all the dailygoals added by a user
    private fun getAllDailyGoals(userId: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${userId}/daily-goals").asString()
    }

    //helper function to update a dailygoal of a user
    private fun updateDailyGoals (id: Int, userId: Int) : HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/${userId}/daily-goals/${id}")
            .body(dailyGoals1)
            .asJson()
    }


}
