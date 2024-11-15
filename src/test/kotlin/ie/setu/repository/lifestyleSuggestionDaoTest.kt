package ie.setu.repository
import ie.setu.domain.db.DailyHabits
import ie.setu.domain.db.Users
import ie.setu.domain.repository.DailyHabitsDAO
import ie.setu.domain.repository.LifestyleSuggestionDAO
import ie.setu.domain.repository.UserDao
import ie.setu.helpers.lifestyleSuggestionsFixtures
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val dailyHabitListOfOneWeek = lifestyleSuggestionsFixtures

class LifeStyleSuggestionTest{

    companion object {
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    internal fun populateUserTable(): UserDao {
        SchemaUtils.create(Users)
        val userDao = UserDao()
        userDao.save(user1)
        userDao.save(user2)
        userDao.save(user3)
        return userDao

      }
    internal fun populateDailyHabits(){

        SchemaUtils.create(DailyHabits)
        val dailyHabitsDao = DailyHabitsDAO()
        dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0])
        dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[1])
        dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[2])
        dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[3])
        dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[4])
        dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[5])
        dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[6])
    }

    @Nested
    inner class ReadLifeStyleSuggestions{

        @Test
        fun `getting dailyhabit data for lifestyle suggestion from an existing user returns all dailyHabits`(){

            transaction {
                populateUserTable()
                populateDailyHabits()
                val userId = 1
                val lifeStyleDao = LifestyleSuggestionDAO()
                val lifeStyleHabits = lifeStyleDao.getlifestyleSuggestions(1)

                assert(lifeStyleHabits.isNotEmpty())
                assertEquals(7,lifeStyleHabits.size)
            }

        }

        @Test
        fun `getting dailyhabit data for lifestyle suggestion with an invalid user id returns empty list or error`() {
            transaction {
                populateUserTable()
                populateDailyHabits()
                val invalidUserId = 44
                val lifeStyleDao = LifestyleSuggestionDAO()
                val lifeStyleHabits = lifeStyleDao.getlifestyleSuggestions(invalidUserId)

                assert(lifeStyleHabits.isEmpty())
                assertEquals(0,lifeStyleHabits.size)
            }
        }

        @Test
        fun `getting lifestyle suggestion with 4 days of data returns just 4 days of data `() {
            transaction {
                populateUserTable()
                SchemaUtils.create(DailyHabits)
                val dailyHabitsDao = DailyHabitsDAO()
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0])
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[1])
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[2])
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[3])


                val userId = 1
                val lifeStyleDao = LifestyleSuggestionDAO()
                val lifeStyleHabits = lifeStyleDao.getlifestyleSuggestions(userId)

              assert(lifeStyleHabits.isNotEmpty())
                assertEquals(4,lifeStyleHabits.size)

            }
        }

        @Test
        fun `getting dailyHabitData for suggestion returns 7 days of data in descending order of date`(){

            transaction {
                populateUserTable()
               SchemaUtils.create(DailyHabits)
                val userId = 1
                val dailyHabitsDao = DailyHabitsDAO()
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0].copy(date= DateTime.parse("2024-11-07T10:00:00"), notes = "this is the seventh entry".toString()))
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0].copy(date= DateTime.parse("2024-11-06T10:00:00"), notes = "this is the sixth entry".toString()))
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0].copy(date= DateTime.parse("2024-11-05T10:00:00"), notes = "this is the fifth entry".toString()))
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0].copy(date= DateTime.parse("2024-11-04T10:00:00"), notes = "this is the fourth entry".toString()))
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0].copy(date= DateTime.parse("2024-11-03T10:00:00"), notes = "this is the third entry".toString()))
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0].copy(date= DateTime.parse("2024-11-02T10:00:00"), notes = "this is the second  entry".toString()))
                dailyHabitsDao.addDailyHabits(dailyHabitListOfOneWeek[0].copy(date= DateTime.parse("2024-11-01T10:00:00"), notes = "this is the first entry".toString()))

                val lifeStyleDao = LifestyleSuggestionDAO()

                val lifeStyleHabits = lifeStyleDao.getlifestyleSuggestions(userId)
                assert(lifeStyleHabits.isNotEmpty())
                assertEquals(7,lifeStyleHabits.size)
                assertEquals("this is the seventh entry",lifeStyleHabits.first().notes)
                assertEquals("this is the first entry",lifeStyleHabits.last().notes)
            }
        }


    }

}