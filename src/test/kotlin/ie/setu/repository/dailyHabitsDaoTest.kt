package ie.setu.repository

import ie.setu.domain.DailyHabit
import ie.setu.domain.db.DailyHabits
import ie.setu.domain.db.Users
import ie.setu.domain.repository.DailyHabitsDAO
import ie.setu.domain.repository.UserDao
import ie.setu.helpers.dailyHabits
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val dailyHabits1 = dailyHabits[0]
val dailyHabits2 = dailyHabits[1]
val dailyHabits3 = dailyHabits[2]

class DailyHabitsDaoTest{

    companion object{

        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateUserTable() {
        SchemaUtils.create(Users)
        val userDao = UserDao()
        userDao.save(user1)
        userDao.save(user2)
        userDao.save(user3)

    }
    internal fun populateDailyHabitsTable():DailyHabitsDAO {

        SchemaUtils.create(DailyHabits)
        val dailyHabitsDao = DailyHabitsDAO()

        dailyHabitsDao.addDailyHabits(dailyHabits1)
        dailyHabitsDao.addDailyHabits(dailyHabits2)
        dailyHabitsDao.addDailyHabits(dailyHabits3)
      return  dailyHabitsDao
    }

    @Nested
    inner class CreateDailyHabits{

        @Test
        fun `creating multiple dailyHabits returns the rows inserted`(){
transaction {  populateUserTable()
    val dailyHabitsDao = populateDailyHabitsTable()

    assertEquals(1, dailyHabitsDao.getAllDailyHabitsByUserId(dailyHabits1.id).size) }

        }
    }

    @Nested
    inner class ReadDailyHabits{

        @Test
        fun `getting dailyHabits for a specific user returns the habits of that user only`(){

            transaction {

                populateUserTable()
            val dailyHabitsDao = populateDailyHabitsTable()

                val userId = 1
                val dailyHabits = dailyHabitsDao.getAllDailyHabitsByUserId(userId)
                assertEquals(1, dailyHabits.size)
                assertEquals(dailyHabits[0].id, dailyHabits1.id)
            }
        }

        @Test
        fun `getting dailyHabits for a user that doesnt exist returns null`(){


            transaction {

                populateUserTable()
                val dailyHabitsDao = populateDailyHabitsTable()
                val userId = 34

                val dailyHabit = dailyHabitsDao.getAllDailyHabitsByUserId(userId)
                assertTrue(dailyHabit.isEmpty())

            }
        }
    }

    @Nested
    inner class UpdateDailyHabits {
        @Test
        fun `updating dailyHabits for a non-existing user returns null`() {

            transaction {
                populateUserTable()
                val dailyHabitsDao = populateDailyHabitsTable()
                val userId = 44
                val newDailyHabit = dailyHabits1.copy(hoursSlept = 3.toDouble())
                val updatedDailyHabit = dailyHabitsDao.updateDailyHabit(1, userId, newDailyHabit)

                assertTrue(updatedDailyHabit == 0)
                assertEquals(7.5, dailyHabits1.hoursSlept)


            }
        }

        @Test
        fun `updating daily habits for a non-existing habit id returns null`() {

            transaction {
                populateUserTable()
                val dailyHabitsDao = populateDailyHabitsTable()
                val id = 44
                val newDailyHabit = dailyHabits1.copy(hoursSlept = 3.toDouble())
                val updatedDailyHabit = dailyHabitsDao.updateDailyHabit(id, 1, newDailyHabit)

                assertTrue(updatedDailyHabit == 0)
                assertEquals(7.5, dailyHabits1.hoursSlept)


            }

        }

        @Test
        fun `updating daily habit for an existing userId returns non empty row`() {

            transaction {

                populateUserTable()
                val dailyHabitsDao = populateDailyHabitsTable()
                val id = 1
                val userId = 1
                val newDailyHabit = dailyHabits1.copy(hoursSlept = 3.toDouble())
                val updatedRowsOfDailyHabit = dailyHabitsDao.updateDailyHabit(id, userId, newDailyHabit)

                assertTrue(updatedRowsOfDailyHabit == 1)

            }
        }

    }


@Nested
inner class DeleteDailyHabits {

    @Test
    fun `Deleting row with a non existing habit-id returns zero rows affected `(){

        transaction {
            populateUserTable()
            val dailyHabitsDao = populateDailyHabitsTable()
            val id = 45
            val userId= 1
            val deletedHabit = dailyHabitsDao.deleteDailyHabit(id,userId)

            assertTrue(deletedHabit == 0)

        }
    }

    @Test
    fun `Deletion with a  non existing user-id  returns zero rows affected `(){

        transaction {
            populateUserTable()
            val dailyHabitsDao = populateDailyHabitsTable()
            val id = 1
            val userId= 45
            val deletedHabit = dailyHabitsDao.deleteDailyHabit(id,userId)

            assertTrue(deletedHabit == 0)

        }
    }
}
    @Test
    fun `Deleting row with an existing habit-id returns non null value `(){

        transaction {
            populateUserTable()
            val dailyHabitsDao = populateDailyHabitsTable()
            val id = 1
            val userId= 1
            val deletedHabit = dailyHabitsDao.deleteDailyHabit(id,userId)

            assertTrue(deletedHabit != 0)
            assertEquals(1,deletedHabit)

        }
    }}