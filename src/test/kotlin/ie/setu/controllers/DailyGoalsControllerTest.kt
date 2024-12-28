package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.DailyGoal
import ie.setu.domain.User
import ie.setu.domain.db.DailyGoals
import ie.setu.helpers.*
import ie.setu.utils.jsonNodeToObject
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
val dailyGoal4 = mapOf(
    "userId" to 1,
    "goalName" to "Morning Workout",
    "goalDescription" to "Complete 30 minutes of cardio and 15 minutes of stretching.",
    "createdAt" to "2024-07-01T10:00:00",
    "isCompleted" to false,
    "date" to "2024-07-01T10:00:00",
    "priority" to 1,
    "notes" to "use the treadmill for cardio"
)
val dailyGoal5 = mapOf(
    "userId" to 1,
    "goalName" to "Updated Goal",
    "goalDescription" to "Updated description",
    "createdAt" to "2024-07-01T10:00:00",
    "isCompleted" to true,
    "date" to "2024-07-01T10:00:00",
    "priority" to 1,
    "notes" to "use the treadmill for cardio"
)


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DailyGoalsControllerTest {
    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()



    @Nested
    inner class ReadDailyGoals {
        @Test
        fun `Getting daily goals for an existing userid returns 200`() {
            // Arrange - dailygoal user id from the fixture
            val userId = dailyGoals1.userId

            // Act - api call to get all daily goals of the user
            val response = getAllDailyGoals(userId)

            // Assert - Check the response status and content
            assertEquals(200, response.status)
            val dailyGoals = jsonToObject<List<DailyGoal>>(response.body)
            assertTrue(dailyGoals.isNotEmpty())
            assertEquals(userId, dailyGoals.first().userId)
        }
        @Test
        fun `Getting daily goals for a non-existing userid returns 404`() {
            // Arrange - 9999 is a non existing id
            val nonExistingUserId = 9999
//            Act - call the api with the non-existing userid
            val response = getAllDailyGoals(nonExistingUserId)

            // Assert - Check the response status
            assertEquals(404, response.status)
        }
        @Test
        fun `Getting daily goals with invalid userid format returns 400`() {

            //Arrange - the user id is in string format instead of integer
            val invalidUserId = "invalidId"
//            Act- call the api to check the response- not calling the helper function here as it expect an int
            val response = Unirest.get(origin + "/api/users/$invalidUserId/daily-goals").asString()

            // Assert - Check the response status
            assertEquals(400, response.status)
        }

    }
    @Nested
    inner class createDailyGoals {

        @Test
        fun `Adding a daily goal for an existing user returns 200`() {
            // Arrange - Add a test user
            val userId = dailyGoals1.userId
            val dailyGoal = dailyGoal4
            //logging for debugging
            exposedLogger.info("this is the user id ----$userId")
            exposedLogger.info("this is the dailygoal ----$dailyGoal")
            // Act - calling api to add the daily goal
            val response = addDailyGoals(userId, dailyGoal)
            exposedLogger.info("Raw response: ${response.body}")
//             Assert - Check the response status and content
            assertEquals(200, response.status)
            val responseBody = jsonNodeToObject<Map<String, Any>>(response)

            assertEquals("true", responseBody["success"])
            assertEquals("daily goal added", responseBody["message"])
            assertNotNull(responseBody["id"])
            exposedLogger.info("Goal id: ${responseBody["id"]}")

            // Verify - retrieve the daily goal back and verify
            val dailyGoalsResponse = getAllDailyGoals(userId)
            val dailyGoalsList = jsonToObject<List<DailyGoal>>(dailyGoalsResponse.body)
            assertTrue(dailyGoalsList.any { it.goalName == dailyGoal["goalName"] })

            // Clean up - Delete the user and their daily goals
            deleteDailyGoals(userId, responseBody["id"] as Int)
            assertEquals(200, response.status)
        }

        @Test
        fun `Adding a daily goal with missing fields returns 400`() {
            // Arrange - Add a test user
            val userId = dailyGoals1.userId
            val invalidDailyGoal = mapOf(
                "goalName" to "Evening Jog",
                "goalDescription" to "Jog for 30 minutes in the park."
                // rest of the fields are incomplete
            )

            // Act - adding a daily goal
            val response = addDailyGoals(userId, invalidDailyGoal)
            exposedLogger.info("Raw response: ${response.body}")

            // Assert - Check response status and content
            assertEquals(400, response.status)
            val responseBody = jsonNodeToObject<Map<String, Any>>(response)
            assertTrue(responseBody.containsKey("error"))
        }

        @Test
        fun `Adding a daily goal with valid data creates it successfully`() {
            // Arrange - Add a test user
            val userId = dailyGoals1.userId
            val dailyGoal = dailyGoal4

            // Act - add a daily goal
            val response = addDailyGoals(userId, dailyGoal)
            exposedLogger.info("Raw response: ${response.body}")

            // Assert - Check response status and content
            assertEquals(200, response.status)
            val responseBody = jsonNodeToObject<Map<String, Any>>(response)
            assertEquals("true", responseBody["success"])
            assertEquals("daily goal added", responseBody["message"])
            assertNotNull(responseBody["id"])

            // Clean up - delete the dailygoal
            deleteDailyGoals(userId, responseBody["id"] as Int)
            assertEquals(200, response.status)
        }

    }

    @Nested
    inner class DeleteDailyGoals{

        @Test
        fun `Deleting an existing daily goal for an existing user returns 200`() {
            // Arrange - Add a test user and daily goal
            val userId = dailyGoals1.userId
            val dailyGoal = dailyGoal4

            val addResponse = addDailyGoals(userId, dailyGoal)
            val addResponseBody = jsonNodeToObject<Map<String, Any>>(addResponse)
            val goalId = addResponseBody["id"] as Int

            // Act - delete the daily goal for the user
            val deleteResponse = deleteDailyGoals(goalId, userId)

            // Assert - Check the response status and validate deletion
            assertEquals(200, deleteResponse.status)
            val dailyGoalsResponse = getAllDailyGoals(userId)
            val dailyGoalsList = jsonToObject<List<DailyGoal>>(dailyGoalsResponse.body)
            assertTrue(dailyGoalsList.none { it.id == goalId })
        }

        @Test
        fun `Deleting a non-existent daily goal returns 400`() {
            // Arrange - Use a non-existent goal ID
            val userId = dailyGoals1.userId
            val nonExistentGoalId = 99999

            // Act - Call the API to delete the non-existent goal
            val deleteResponse = deleteDailyGoals(nonExistentGoalId, userId)
            assertEquals(400, deleteResponse.status)
            // Assert - Check the response status and error message
            val responseBody = jsonToObject<Map<String, String>>(deleteResponse.body)
            assertTrue(responseBody.containsKey("error"))
        }

        @Test
        fun `Deleting a daily goal with invalid userId returns 400`() {
            // Arrange - using an invalid user id
            val invalidUserId = -1
            val goalId = 1

            // Act - delete the dailygoal with the invalid parameters
            val deleteResponse = deleteDailyGoals(goalId, invalidUserId)

            // Assert - Check the response status and error message
            assertEquals(400, deleteResponse.status)
            val responseBody = jsonToObject<Map<String, String>>(deleteResponse.body)
            assertTrue(responseBody.containsKey("error"))

        }

        @Test
        fun `Deleting a daily goal with invalid goalId returns 400`() {
            // Arrange - Use an invalid goal ID
            val userId = dailyGoals1.userId
            val invalidGoalId = -1

            // Act - Call the api to delete the daily goal with invalid goalId
            val deleteResponse = deleteDailyGoals(invalidGoalId, userId)

            // Assert - Check the response status and error message
            assertEquals(400, deleteResponse.status)
            val responseBody = jsonToObject<Map<String, String>>(deleteResponse.body)
            assertTrue(responseBody.containsKey("error"))

        }

    }

    @Nested
    inner class UpdateDailyGoals{

        @Test
        fun `Updating an existing daily goal for an existing user returns 200`() {

            // Arrange - Add a test user and daily goal
            val userId = dailyGoals1.userId
            val initialDailyGoal = dailyGoal4
            val addResponse = addDailyGoals(userId, initialDailyGoal)
            val addResponseBody = jsonNodeToObject<Map<String, Any>>(addResponse)
            val goalId = addResponseBody["id"] as Int

            // Update daily goal details
            val updatedDailyGoal =dailyGoal5

            // Act - Call the API to update the daily goal
            val updateResponse = updateDailyGoals(goalId, userId, updatedDailyGoal)
              exposedLogger.info(updateResponse.body.toString())
            // Assert - Check the response status and content
            assertEquals(200, updateResponse.status)
            val responseBody = jsonNodeToObject<Map<String, String>>(updateResponse)
            assertTrue(responseBody.containsKey("success"))


            // Verify - Retrieve and validate the updated goal
            val dailyGoalsResponse = getAllDailyGoals(userId)
            val dailyGoalsList = jsonToObject<List<DailyGoal>>(dailyGoalsResponse.body)
            val updatedGoal = dailyGoalsList.find { it.id == goalId }
            assertNotNull(updatedGoal)
            assertEquals("Updated Goal", updatedGoal?.goalName)
            assertEquals("Updated description", updatedGoal?.goalDescription)
            assertEquals(true, updatedGoal?.isCompleted)


        }

        @Test
        fun `Updating a daily goal with invalid userId returns 400`() {
            // Arrange - Use an invalid user ID
            val invalidUserId = -1
            val goalId = 1
            val updatedDailyGoal = dailyGoal4.toMutableMap()

            // Act - Call the API with invalid user ID
            val updateResponse = updateDailyGoals(goalId, invalidUserId, updatedDailyGoal)

            // Assert - Check the response status and error message
            assertEquals(400, updateResponse.status)
            val responseBody = jsonToObject<Map<String, String>>(updateResponse.body.toString())
            assertTrue(responseBody.containsKey("error"))

        }

        @Test
        fun `Updating a daily goal with invalid goalId returns 400`() {
            // Arrange - Use an invalid goal ID
            val userId = dailyGoals1.userId
            val invalidGoalId = -1
            val updatedDailyGoal = dailyGoal4.toMutableMap()

            // Act - Call the API with invalid goal ID
            val updateResponse = updateDailyGoals(invalidGoalId, userId, updatedDailyGoal)

            // Assert - Check the response status and error message
            assertEquals(400, updateResponse.status)
            val responseBody = jsonToObject<Map<String, String>>(updateResponse.body.toString())
            assertTrue(responseBody.containsKey("error"))

        }

    }








//    helper function to add a daily goal to a user
    private fun addDailyGoals (userId:Int,dailyGoal:Map<String, Any>): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users/${userId}/daily-goals")
            .body(dailyGoal).asJson()

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
    private fun updateDailyGoals (id: Int, userId: Int,dailyGoal: Map<String, Any>) : HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/${userId}/daily-goals/${id}")
            .body(dailyGoal)
            .asJson()
    }


}
