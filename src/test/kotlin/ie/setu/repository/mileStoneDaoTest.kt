package ie.setu.repository

import ie.setu.domain.db.MileStones
import ie.setu.domain.db.Users
import ie.setu.domain.repository.MileStonesDAO
import ie.setu.domain.repository.UserDao
import ie.setu.helpers.mileStoneFixtures
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


val mileStone1 = mileStoneFixtures[0]
val mileStone2 = mileStoneFixtures[0]
val mileStone3 = mileStoneFixtures[0]
val mileStone4 = mileStoneFixtures[0]
class mileStoneDaoTest {

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

    internal fun populateMileStoneTable():MileStonesDAO{

        SchemaUtils.create(MileStones)
        val mileStoneDao=MileStonesDAO()
        mileStoneDao.addNewMileStone(mileStone1)
        mileStoneDao.addNewMileStone(mileStone2)
        mileStoneDao.addNewMileStone(mileStone3)
        return mileStoneDao

    }
    @Nested
    inner class CreateMileStones{

        @Test
        fun`creating milestones for an existing user returns rows created`(){
            transaction {
         populateUserTable()
         val mileStoneDao =   populateMileStoneTable()
         val userId =1
         val rowsCreated = mileStoneDao.getMilestonesByUserId(userId)
         assertEquals(3, rowsCreated.size)


}

        }
    }

    @Nested
    inner class ReadMileStones{

        @Test
        fun `getting milestones from an existing user id returns non null values`(){

            transaction {
                populateUserTable()
                val mileStoneDao =   populateMileStoneTable()
                val userId =1
                val totalMileStones = mileStoneDao.getMilestonesByUserId(userId)

                assertTrue(totalMileStones.isNotEmpty())
                assertEquals(3, totalMileStones.size)
            }
        }

        @Test
        fun `getting milestones from a non  existing user id returns null values`(){

            transaction {
                populateUserTable()
                val mileStoneDao =   populateMileStoneTable()
                val userId =45
                val totalMileStones = mileStoneDao.getMilestonesByUserId(userId)

                assertTrue(totalMileStones.isEmpty())
                assertEquals(0, totalMileStones.size)
            }
        }
    }
    @Nested
    inner class UpdateMileStones{

        @Test
        fun `updating milestones for an existing user returns updated rows`(){

            transaction {
                populateUserTable()
                val mileStoneDao = populateMileStoneTable()
                val userId =1
                val updatedMileStones = mileStoneDao.updateMilestone(1,1,mileStone1.copy(notes = "milestone updated"))
                val totalLists = mileStoneDao.getMilestonesByUserId(userId)
                assertEquals(3,totalLists.size)
                assertTrue(updatedMileStones == 1)
                val updatedMilestone = totalLists.first { it.id == 1 }
                assertEquals("milestone updated", totalLists[0].notes)
            }
        }

        @Test
        fun `updating milestones for a non existing user returns no rows`(){

            transaction {
                populateUserTable()
                val mileStoneDao = populateMileStoneTable()
                val userId =45
                val updatedMileStones = mileStoneDao.updateMilestone(id=1 , userId=userId, mileStone1.copy(notes = "milestone updated"))
                val totalLists = mileStoneDao.getMilestonesByUserId(userId)
                assertEquals(0,totalLists.size)
                assertEquals("Achieved this milestone in 10 days of consistent walking.", mileStone1.notes)
            }
        }
    }
}