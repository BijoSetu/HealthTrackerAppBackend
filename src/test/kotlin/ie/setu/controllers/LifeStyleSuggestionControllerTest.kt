package ie.setu.controllers

import ie.setu.config.DbConfig

import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import kong.unirest.core.HttpResponse
import kong.unirest.core.Unirest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LifeStyleSuggestionControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

@Nested
inner class ReadLifeStyleSuggestion {

    @Test
    fun `should return error when userId is invalid`() {
//        545 is an invalid userid , it doesnt exist in  the db
//        Act
        val response =getLifeStyleSuggestion(545)
//        Assert
        assertEquals(400, response.status)

    }

    @Test
    fun `should return insufficient data message when habits are less than a week`() {
//        this user does not have enough daily habit data on the db
//        Arrange
        val userId = 2
//        Act
        val response = getLifeStyleSuggestion(userId)
        val responseBody = jsonToObject<Map<String, Any>>(response.body.toString())
//        Assert
        assertEquals(400, response.status)
        assertTrue(responseBody.containsValue("Not enough data"))
    }

    @Test
    fun `should return valid lifestyle suggestions based on user's habits`() {
//        this user has more than 7 days of daily habit in the db
//        Arrange
        val userId = 1
//        Act
        val response = getLifeStyleSuggestion(userId)
        val responseBody = jsonToObject<Map<String, Any>>(response.body.toString())
//        Assert
        assertEquals(200, response.status)
        assertTrue(responseBody.containsKey("suggestions"))

    }

    @Test
    fun `should return error when an unexpected exception occurs`() {
//      Arrange -   non-existent user
        val userId = 999
//        Act
        val response = getLifeStyleSuggestion(userId)
        val responseBody = jsonToObject<Map<String, Any>>(response.body.toString())
//        Assert
        assertEquals(400, response.status)
        assertTrue(responseBody.containsKey("error"))
    }

}





    //helper function to get lifestyle suggestion for a user
    private fun getLifeStyleSuggestion(userId: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${userId}/life-style-suggestion").asString()
    }
}