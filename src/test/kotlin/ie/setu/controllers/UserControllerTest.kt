package ie.setu.controllers

import ie.setu.config.DbConfig
import ie.setu.domain.User
import ie.setu.helpers.*
import ie.setu.utils.jsonToObject
import kong.unirest.core.HttpResponse
import kong.unirest.core.JsonNode
import kong.unirest.core.Unirest
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
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




    @Nested
    inner class CreateUsers {

        @Test
        fun `add a user with correct details returns a 201 response`() {

            //Arrange & Act & Assert
            //    add the user and verify return code (using fixture data)
            val addResponse = addUser(validName, validEmail,validPassword)
            assertEquals(201, addResponse.status)

//            Assert - retrieve the added user from the database and verify return code
            val retrieveResponse= retrieveUserByEmail(validEmail)
            println(retrieveResponse.body)
            assertEquals(200, retrieveResponse.status)

            //Assert - verify the contents of the retrieved user
            val retrievedUser : User = jsonToObject(retrieveResponse.body.toString())
            assertEquals(validEmail, retrievedUser.email)
            assertEquals(validName, retrievedUser.name)

            //After - restore the db to previous state by deleting the added user
            val deleteResponse = deleteUser(retrievedUser.userid)
            assertEquals(200, deleteResponse.status)

        }

    }

    @Nested
    inner class ReadUsers {
        //tests go in here

        @Test
        fun `get all users from the database returns 200 or 404 response`() {
            val response = Unirest.get(origin + "/api/users/").asString()
            if (response.status == 200) {
                val retrievedUsers: ArrayList<User> = jsonToObject(response.body.toString())
                assertNotEquals(0, retrievedUsers.size)
            }
            else {
                assertEquals(404, response.status)
            }
        }

        @Test
        fun `get user by id when user does not exist returns 404 response`() {

            //Arrange - test data for user id
            val id = Integer.MIN_VALUE

            // Act - attempt to retrieve the non-existent user from the database
            val retrieveResponse = Unirest.get(origin + "/api/users/${id}").asString()

            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `get user by email when user does not exist returns 404 response`() {
            // Arrange & Act - attempt to retrieve the non-existent user from the database
            val retrieveResponse = Unirest.get(origin + "/api/users/email/${nonExistingEmail}").asString()
            // Assert -  verify return code
            assertEquals(404, retrieveResponse.status)
        }

        @Test
        fun `getting a user by id when id exists, returns a 200 response`() {

            //Arrange - add the user
            val addResponse = addUser(validName, validEmail,validPassword)
            val addedUser : User = jsonToObject(addResponse.body.toString())
            exposedLogger.info(addedUser.toString())
            //Assert - retrieve the added user from the database and verify return code
            val retrieveResponse = retrieveUserById(addedUser.userid)
            assertEquals(200, retrieveResponse.status)

            //After - restore the db to previous state by deleting the added user
            deleteUser(addedUser.userid)
        }

        @Test
        fun `getting a user by email when email exists, returns a 200 response`() {

            //Arrange - add the user
            addUser(validName, validEmail,validPassword)

            //Assert - retrieve the added user from the database and verify return status
            val retrieveResponse = retrieveUserByEmail(validEmail)
            assertEquals(200, retrieveResponse.status)

            //After - restore the db to previous state by deleting the added user
            val retrievedUser : User = jsonToObject(retrieveResponse.body.toString())
            deleteUser(retrievedUser.userid)
        }

    }
//
    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating a user when it exists, returns a 200 response`() {

            //Arrange - add the user that will updated later
            val addedResponse = addUser(validName, validEmail,validPassword)
            val addedUser : User = jsonToObject(addedResponse.body.toString())
            exposedLogger.info("this is the addeduser : $addedUser")
            //Act & Assert - update the email and name of the retrieved user and assert 200
            assertEquals(200, updateUser(addedUser.userid, validName, validEmail).status)
            exposedLogger.info("this is the status : ${updateUser(addedUser.userid, validName, validEmail).status}")
            //Act & Assert - retrieve updated user and assert details are correct
            val updatedUserResponse = retrieveUserById(addedUser.userid)

            exposedLogger.info("this is the updated userresponse : $updatedUserResponse")
            val updatedUser : User = jsonToObject(updatedUserResponse.body.toString())
            exposedLogger.info("this is the updated user : $updatedUser")
            assertEquals(validName, updatedUser.name)
            assertEquals(validEmail, updatedUser.email)

            //After - restore the db to previous state by deleting the added user
        val  deleteResponse=   deleteUser(addedUser.userid)
            assertEquals(200, deleteResponse.status)
        }

        @Test
        fun `updating a user when it doesn't exist, returns a 400 response`() {

            //Act & Assert - attempt to update the email and name of user that doesn't exist
            assertEquals(400, updateUser(-1, validName, validEmail,).status)
        }
    }

    @Nested
    inner class DeleteUsers {

        @Test
        fun `deleting a user when it doesn't exist, returns a 404 response`() {
            //Act & Assert - attempt to delete a user that doesn't exist
            assertEquals(400, deleteUser(-1).status)
        }

        @Test
        fun `deleting a user when it exists, returns a 204 response`() {

            //Arrange - add the user to delete later on
            val addedResponse = addUser(validName, validEmail,validPassword)
            val addedUser : User = jsonToObject(addedResponse.body.toString())
           exposedLogger.info(addedUser.toString())
            //Act & Assert - delete the added user and assert 200 is the response
            assertEquals(200, deleteUser(addedUser.userid).status)

            //Act & Assert - attempt to retrieve the deleted user will give a  404 response
            assertEquals(404, retrieveUserById(addedUser.userid).status)
        }
    }

    //helper function to add a test user to the database
    private fun addUser (name: String, email: String, password: String): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users/signup")
            .body("{\"name\":\"$name\", \"email\":\"$email\", \"password\":\"$password\"}")
            .asJson()
    }

    //helper function to delete a test user from the database
    private fun deleteUser (id: Int): HttpResponse<String> {
        return Unirest.delete(origin + "/api/users/${id}").asString()
    }

    //helper function to retrieve a test user from the database by email
    private fun retrieveUserByEmail(email : String) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/email/${email}").asString()
    }

    //helper function to retrieve a test user from the database by id
    private fun retrieveUserById(id: Int) : HttpResponse<String> {
        return Unirest.get(origin + "/api/users/id/${id}").asString()
    }

    //helper function to add a test user to the database
    private fun updateUser (id: Int, name: String, email: String): HttpResponse<JsonNode> {
        return Unirest.patch(origin + "/api/users/$id")
            .body("{\"name\":\"$name\", \"email\":\"$email\"}")
            .asJson()
    }

}