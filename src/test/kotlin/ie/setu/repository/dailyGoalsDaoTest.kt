package ie.setu.repository

import ie.setu.domain.db.DailyGoals
import ie.setu.domain.db.Users
import ie.setu.domain.repository.DailyGoalsDAO
import ie.setu.domain.repository.UserDao
import ie.setu.helpers.dailyGoalsTestData
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


val dailyGoals1 = dailyGoalsTestData[0]
val dailyGoals2 = dailyGoalsTestData[1]
val dailyGoals3 = dailyGoalsTestData[2]
class DailyGoalsDaoTest {

    companion object {
        //Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }


    }


    internal fun populateDailyGoalsTable(): DailyGoalsDAO {
        SchemaUtils.create(DailyGoals)
        val dailyGoalsDAO = DailyGoalsDAO()
        dailyGoalsDAO.addDailyGoals(dailyGoals1)
        dailyGoalsDAO.addDailyGoals(dailyGoals2)
        dailyGoalsDAO.addDailyGoals(dailyGoals3)
        return dailyGoalsDAO
    }

    internal fun populateUserTable() {
        SchemaUtils.create(Users)
        val userDao = UserDao()
        userDao.save(user1)
        userDao.save(user2)
        userDao.save(user3)

    }

    @Nested
    inner class CreateDailyGoals{
        @Test
        fun `multiple dailygoals added to table can be retrieved successfully`(){

            transaction {
                populateUserTable()
                val dailyGoals = populateDailyGoalsTable()

                assertEquals(3,dailyGoals.getAllDailyGoalsByUserId(1).size)
                Assertions.assertEquals(dailyGoals1, dailyGoals.getAllDailyGoalsByUserId(dailyGoals1.userId)[0])
                Assertions.assertEquals(dailyGoals2, dailyGoals.getAllDailyGoalsByUserId(dailyGoals1.userId)[1])
                Assertions.assertEquals(dailyGoals3, dailyGoals.getAllDailyGoalsByUserId(dailyGoals1.userId)[2])  }


        }

    }

    @Nested
    inner class ReadDailyGoals{
@Test
        fun `getting daily goals for a specific user returns only that users' dailygoals`(){

            transaction {

                populateUserTable()
                val dailyGoals = populateDailyGoalsTable()

                val readGoals = dailyGoals.getAllDailyGoalsByUserId(1)
                val userId = dailyGoals1.userId
                assertEquals(3,dailyGoals.getAllDailyGoalsByUserId(1).size)
                assertTrue(readGoals.all { it.userId == userId })

            }


        }

        @Test
        fun `getting daily goals for a user that doesn't have daily goals returns null`(){


            transaction {

                populateUserTable()
                val dailyGoals = populateDailyGoalsTable()
                val userId = 3
                val readGoals = dailyGoals.getAllDailyGoalsByUserId(3)
                assertEquals(3,dailyGoals.getAllDailyGoalsByUserId(1).size)
                assertEquals(0,  dailyGoals.getAllDailyGoalsByUserId(3).size)
            }
        }
    }

    @Nested
    inner  class UpdateDailyGoals{
        @Test
        fun `updating  an existing daily goals' priority returns updated priority`()
        {
          transaction {
             populateUserTable()
    val dailyGoals = populateDailyGoalsTable()
    val dailyGoalChanges = dailyGoals1.copy(priority = 1, isCompleted = true)
    val updatedRows = dailyGoals.updateDailyGoal(dailyGoals1.id, dailyGoals1.userId, dailyGoalChanges)
    val updatedGoal = dailyGoals.getAllDailyGoalsByUserId(dailyGoals1.userId).first { it.id == dailyGoals1.id }
    assertEquals(1, updatedRows)
    assertEquals(1, updatedGoal.priority)
    assertEquals(true,updatedGoal.isCompleted)
}

        }

        @Test
        fun `updating  a non-existing daily goal returns zero`() {
            transaction {
                populateUserTable()
            val dailyGoalsDao =     populateDailyGoalsTable()

                val nonExistingGoalId = 14
                val updateAttempt = dailyGoalsDao.updateDailyGoal(nonExistingGoalId, 1, dailyGoals1)

                assertEquals(0, updateAttempt)
            }
        }

    }

    @Nested
    inner class DeleteDailyGoals {

        @Test
        fun `deleting an existing daily goal by id and userId returns success`() {
            transaction {
                populateUserTable()
              val dailyGoalsDao =   populateDailyGoalsTable()

                val rowsDeleted = dailyGoalsDao.deleteDailyGoal(dailyGoals1.id, dailyGoals1.userId)

                assertEquals(1, rowsDeleted)
                val retrievedGoals = dailyGoalsDao.getAllDailyGoalsByUserId(dailyGoals1.userId)
                assertFalse(retrievedGoals.any { it.id == dailyGoals1.id })
            }
        }

        @Test
        fun `deleting a non-existing daily goal by id returns zero rows affected`() {
            transaction {
                populateUserTable()
                val dailyGoalsDao =   populateDailyGoalsTable()

                val nonExistingGoalId = 114
                val rowsDeleted = dailyGoalsDao.deleteDailyGoal(nonExistingGoalId, 1)

                assertEquals(0, rowsDeleted)
            }
        }
    }

}