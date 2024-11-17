package ie.setu.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import ie.setu.config.DbConfig
import ie.setu.domain.User
import ie.setu.helpers.ServerContainer
import kong.unirest.core.HttpResponse
import kong.unirest.core.JsonNode
import kong.unirest.core.Unirest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    private val db = DbConfig().getDbConnection()
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    private fun addUser (name: String, email: String,password:String): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users")
            .body("{\"name\":\"$name\", \"email\":\"$email\",{\"password\":\"$password\"}")
            .asJson()
    }
    private fun deleteUser (id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/$id").asString()
    }

    private fun retrieveUserById(id: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/${id}").asString()
    }

//    code taken from  article : https://www.baeldung.com/unirest for setting objectmapper for unirest
//    fun configureUnirest() {
//       Unirest.config().setObjectMapper(object : ObjectMapper {
//           var mapper
//                   : com.fasterxml.jackson.databind.ObjectMapper = com.fasterxml.jackson.databind.ObjectMapper()
//
//           override fun writeValue(value: Any): String {
//               return mapper.writeValueAsString(value)
//           }
//
//           override fun <T> readValue(value: String, valueType: Class<T>): T {
//               return mapper.readValue(value, valueType)
//           }
//       }   )
//    }
    @Nested
    inner class ReadUsers{

//        @Test
//        fun `get all users from the database returns 200 or 404 response`() {
//            val response = Unirest.get(origin + "/api/users/").asString()
//            assertEquals(200, response.status)
//        }

        @Test
        fun `get user by id when user does not exist returns 404 response`() {

            val user_id = 45

            val retrieveResponse = Unirest.get(origin + "/api/users/${user_id}").asString()

            assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `get all users from the database returns 200 or 404 response`() {

            val response = Unirest.get(origin + "/api/users/").asString()
            if (response.status == 200) {
                val mapper = jacksonObjectMapper()

                val retrievedUsers: ArrayList<User> =  mapper.readValue(response.body, object : TypeReference<ArrayList<User>>() {})
                assertNotEquals(0, retrievedUsers.size)
            }
            else {
                assertEquals(404, response.status)
            }
        }
    }

    @Nested
    inner class CreateUser{

        @Test

        fun `add a user with correct details returns a 201 response`() {

            //Arrange & Act & Assert
            //    add the user and verify return code (using fixture data)
           val validName = "John"
            val     validEmail = "john@example.com"
            val validPassword = "password"
            val addResponse = addUser(validName, validEmail,validPassword)

            assertEquals(201, addResponse.status)

            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse= retrieveUserById(1)
            assertEquals(200, retrieveResponse.status)

            //Assert - verify the contents of the retrieved user
            val mapper = jacksonObjectMapper()
            val retrievedUser : User = mapper.readValue(addResponse.body.toString(), object : TypeReference<User>() {})
            assertEquals(validEmail, retrievedUser.email)
            assertEquals(validName, retrievedUser.name)

            //After - restore the db to previous state by deleting the added user
            val deleteResponse = deleteUser(retrievedUser.user_id)
            assertEquals(204, deleteResponse.status)
        }
    }

}