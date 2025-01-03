package ie.setu.repository
import ie.setu.domain.PayloadLogin
import ie.setu.domain.PayloadUpdate
import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.domain.repository.UserDao
import ie.setu.helpers.nonExistingEmail
import ie.setu.helpers.payloadLogin
import ie.setu.helpers.users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

val user1 = users[0]
val user2 = users[1]
val user3 = users[2]
val loginPayload = payloadLogin[0]
class UsersDaoTest {


    companion object {
    //Make a connection to a local, in memory H2 database.
    @BeforeAll
    @JvmStatic
    internal fun setupInMemoryDatabaseConnection() {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
    }


}


    internal fun populateUserTable(): UserDao{
        SchemaUtils.create(Users)
        val userDao = UserDao()
        userDao.save(user1)
        userDao.save(user2)
        userDao.save(user3)
        return userDao
    }

    @Nested
    inner class CreateUsers{

        @Test
        fun `creating user with an email that already exists in db returns error`(){
            val newUserWithExistingEmail = User(name = "john", email ="alice@wonderland.com", userid = 4, password = "Password123")
            transaction {

             val userDao=  populateUserTable()

                assertEquals(3, userDao.getAll().size)
                assertEquals(null, userDao.registerNewUser(newUserWithExistingEmail))
            }
        }

        @Test
        fun `multiple users added to table can be retrieved successfully`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDao=  populateUserTable()

                //Act & Assert
                assertEquals(3, userDao.getAll().size)
                assertEquals(user1, userDao.getUserById(user1.userid))
                assertEquals(user2, userDao.getUserById(user2.userid))
                assertEquals(user3, userDao.getUserById(user3.userid))
            }

        }
    }

    @Nested

    inner class ReadUsers{

@Test
fun `logging in a user with an existing and matching email and password returns success ` (){

    transaction {
        val userDao=  populateUserTable()

        assertEquals(3, userDao.getAll().size)
        assertNotEquals(null,userDao.loginUser(loginPayload))
    }
}
        @Test
        fun `get all users over empty table returns none`() {
            transaction {

                //Arrange - create and setup userDAO object
                SchemaUtils.create(Users)
                val userDAO = UserDao()

                //Act & Assert
                assertEquals(0, userDAO.getAll().size)
            }
        }

        @Test
        fun `get user by email that doesn't exist, results in no user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(null, userDAO.findByEmail(nonExistingEmail))
            }
        }

        @Test
        fun `get user by email that exists, results in correct user returned`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(user2, userDAO.findByEmail(user2.email))
            }
        }

        @Test
        fun  `getting all users from a table returns all available rows` () {
            transaction {
                val userDao=  populateUserTable()
                assertEquals(3, userDao.getAll().size)
            }

        }

        @Test
        fun  `searching for user with id that does not exist returns null` () {

            transaction {
                val userDao=  populateUserTable()

                assertEquals(null, userDao.getUserById(4))
            }
        }
        @Test
        fun  `searching user by id that exists returns a valid user` () {

            transaction {
                val userDao=  populateUserTable()
                assertEquals(user2, userDao.getUserById(2))
            }
        }

    }

    @Nested
    inner class UpdateUsers {

        @Test
        fun `updating existing user in table results in successful update`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()

                //Act & Assert
                val user3Updated = PayloadUpdate( email = "bob@gmail.com", name = "bob")
                userDAO.updateUser(user3.userid, user3Updated)
                val updatedUser =  userDAO.getUserById(3)
                assertEquals( "bob@gmail.com", updatedUser!!.email)
            }
        }

        @Test
        fun `updating non-existent user in table results in no updates`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()

                //Act & Assert
                val user4Updated = PayloadUpdate( email = "bob@gmail.com",name="bob")
                userDAO.updateUser(4, user4Updated)
                assertEquals(null, userDAO.getUserById(4))
                assertEquals(3, userDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class DeleteUsers{

        @Test
        fun `deleting non existing user results in no deletion`   (){

            transaction {

                val userDao=  populateUserTable()
                assertEquals(3, userDao.getAll().size)
                assertEquals(3, userDao.getAll().size)

            }
        }
        @Test
        fun `deleting an existing user in table results in record being deleted`() {
            transaction {

                //Arrange - create and populate table with three users
                val userDAO = populateUserTable()

                //Act & Assert
                assertEquals(3, userDAO.getAll().size)
                println("number of deleted users ${ userDAO.deleteUser(2)}")
                assertEquals(2, userDAO.getAll().size)
            }
        }
    }


}