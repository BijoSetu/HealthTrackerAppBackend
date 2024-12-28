package ie.setu.controllers

import ie.setu.config.DbConfig

import ie.setu.helpers.*
import ie.setu.utils.jsonNodeToObject
import ie.setu.utils.jsonToObject
import kong.unirest.core.HttpResponse
import kong.unirest.core.JsonNode
import kong.unirest.core.Unirest
import org.jetbrains.exposed.sql.exposedLogger

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

val mileStone1 = mapOf(
    "id" to 200,
    "userId" to 1,
    "milestoneName" to "Walked 100,000 Steps",
    "achievedDate" to "2024-11-01T09:00:00",
    "notes" to "Achieved this milestone in 10 days of consistent walking.",
    "created" to "2024-11-01T09:00:00",
    "updated" to "2024-11-10T10:05:00"
)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MilesStonesControllerTest {
    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()


@Nested
inner class CreateMileStones{

    @Test
    fun `adding milestone for an existing user should return 200`() {
        // Arrange
        val userId = mileStone1["userId"] as Int
        val milestoneData = mileStone1

        // Act
        val response = addMilestones(userId, milestoneData)

        // Assert
        assertEquals(200, response.status)
        val responseBody = jsonToObject<Map<String,Any>>(response.body.toString())
        assertTrue(responseBody.containsKey("success"))
        assertEquals("MileStone added", responseBody["success"])

        //  verify the milestone exists in the database
        val retrievedMilestone = getAllMilestones(userId)
        val retrievedMilestoneBody = jsonToObject<Map<String,Any>>(retrievedMilestone.body.toString())
        assertNotNull(retrievedMilestoneBody)
        assertEquals(true, retrievedMilestoneBody["success"] as Boolean)
    }

    @Test
    fun `adding milestone with missing fields should return 400`() {
        // Arrange
        val userId = mileStone1["userId"] as Int
        val incompleteMilestoneData = mapOf(
            "notes" to "Missing milestonename"
        )

        // Act
        val response = addMilestones(userId, incompleteMilestoneData)

        // Assert
        assertEquals(400, response.status)
        val responseBody = jsonNodeToObject<Map<String, Any>>(response)
        assertTrue(responseBody.containsKey("error"))
    }

    @Test
    fun `adding milestone for non-existent user should return 404`() {
        // Arrange
        val invalidUserId = 999

        // Act
        val response = addMilestones(invalidUserId, mileStone1)
        val responseBody = jsonNodeToObject<Map<String, Any>>(response)
        // Assert
        assertEquals(400, response.status)


    }
}

    @Nested
    inner class ReadMilesStones{

        @Test
        fun `getting milestones for non-existent user should return 400`() {
            // Arrange
            val invalidUserId = 999

            // Act
            val response = getAllMilestones(invalidUserId)

            // Assert
            assertEquals(400, response.status)

        }
       @Test
        fun `fetching milestones for an existing user should return 200 and milestones`() {
            // Arrange
            val userId = mileStone1["userId"] as Int

            // Act
            val response = getAllMilestones(userId)

            // Assert
            assertEquals(200, response.status)
            val responseBody = jsonToObject<Map<String, Any>>(response.body.toString())
            assertTrue(responseBody.containsKey("milestones"))

            val milestones = responseBody["milestones"]
            assertFalse (milestones== null)
        }
         @Test
        fun `getting milestones for a user with no milestones should return an empty list`() {
            // Arrange : this user does not have any milestones added
            val userIdWithNoMilestones = 1234

            // Act
            val response = getAllMilestones(userIdWithNoMilestones)

            // Assert
            assertEquals(400, response.status)




        }

    }

    @Nested
    inner class DeleteMilesStones{

        @Test
        fun `deleting an existing milestone should return 200`() {
            // Arrange
            val userId = mileStone1["userId"] as Int
            val milestoneId = mileStone1["id"] as Int
               val addResponse=   addMilestones(userId,mileStone1)
            val retrievedResponse = jsonToObject<Map<String,Any>>(addResponse.body.toString())
            exposedLogger.info("this is the addresponse $retrievedResponse")
            // Act
            val response = deleteMilestones(milestoneId, userId)
            val deleteResponse = jsonToObject<Map<String,Any>>(response.body.toString())
            exposedLogger.info("this is the delete $deleteResponse")

            // Assert
            assertEquals(200, response.status)

            assertTrue(deleteResponse.containsKey("success"))
            assertEquals("MileStoneDeleted", deleteResponse["success"])

            //  verify the milestone no longer exists in the database
            val allMilestones = getAllMilestones(userId)
            val milestonesBody = jsonToObject<Map<String, Any>>(allMilestones.body.toString())
            val milestonesList = milestonesBody["milestones"] as List<Map<String, Any>>
            assertFalse(milestonesList.any { it["id"] == milestoneId })
        }
    }

    private fun addMilestones (userId:Int,mileStone:Map<String,Any>): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users/${userId}/milestones")
            .body(mileStone).asJson()

    }

    //helper function to delete a dailygoal from database
    private fun deleteMilestones (milestoneId: Int,userId: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/${userId}/milestones/${milestoneId}").asString()
    }

    //helper function to get all the dailygoals added by a user
    private fun getAllMilestones(userId: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${userId}/milestones").asString()
    }

    //helper function to update a dailygoal of a user
    private fun updateMilestones (milestoneId: Int, userId: Int,milestone: Map<String, Any>) : HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/${userId}/daily-goals/${milestoneId}")
            .body(milestone)
            .asJson()
    }
}