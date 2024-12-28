package ie.setu.repository

import ie.setu.domain.UserAttribute
import ie.setu.domain.db.UserAttributes
import ie.setu.domain.db.Users
import ie.setu.domain.repository.UserAttributesDao
import ie.setu.domain.repository.UserDao
import ie.setu.helpers.userAttributesFixtures
import ie.setu.utils.jsonToObject
import org.jetbrains.exposed.sql.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.jetbrains.exposed.sql.transactions.transaction

val userAttribute= userAttributesFixtures[0]

class UserAttributeDaoTest {

    companion object {
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
    internal fun populateUserAttributesTable(): UserAttributesDao {
        SchemaUtils.create(UserAttributes)
        val userAttributeDao = UserAttributesDao()

        // Add the fixtures data to the database
        userAttributeDao.addUserAttribute(userAttribute)

        return userAttributeDao
    }



    @Nested
    inner class UpdateUserAttribute {

        @Test
        fun `updating an existing user attribute updates the value correctly`() {
            transaction {
                val userAttributeDao = populateUserAttributesTable()

                val updatedAttribute = UserAttribute(
                    id = 1,
                    userId = 1,
                    age = 26,
                    gender = "Male",
                    weight = 72.0f,
                    height = 175.0f
                )

                userAttributeDao.updateUserAttribute(1,updatedAttribute)

                // get the updated attribute
                val updatedUserAttribute = userAttributeDao.getUserAttributes(1)
                assertEquals(26, updatedUserAttribute?.age)
                assertEquals(72.0f, updatedUserAttribute?.weight)
            }
        }
    }

    @Nested
    inner class DeleteUserAttribute {

        @Test
        fun `deleting a user attribute removes it from the database`() {
            transaction {
                val userAttributeDao = populateUserAttributesTable()

                val userAttribute = userAttributeDao.getUserAttributes(1)
                assertTrue(userAttribute != null)

                // Deleting an attribute
                userAttributeDao.deleteUserAttribute(1)

                // Fetch user attributes after delete
                val userAttributeafterDeletion = userAttributeDao.getUserAttributes(1)
                assertEquals(null,userAttributeafterDeletion)
            }
        }
    }

    @Nested
    inner class GetUserAttribute {
        @Test
        fun `getting a user attribute by ID returns the correct attribute`() {
            transaction {
                val userAttributeDao = populateUserAttributesTable()

                // get user attribute by ID
                val attribute = userAttributeDao.getUserAttributes(1)

//          this is the json that is expectd
                val expectedJson = """{"id":1,"userId":1,"age":25,"gender":"Male","weight":70.5,"height":175.0}"""
                val expected = jsonToObject<UserAttribute>(expectedJson)

                // Assert that both matches
                assertEquals(expected, attribute)
            }
        }

        @Test
        fun `getting user attribute by userId returns correct attribute`() {
            transaction {
                val userAttributeDao = populateUserAttributesTable()

                // get user attribute by userId
                val attribute = userAttributeDao.getUserAttributes(1)

                //          this is the json that is expected
                val expectedJson = """{"id":1,"userId":1,"age":25,"gender":"Male","weight":70.5,"height":175.0}"""
                val expected = jsonToObject<UserAttribute>(expectedJson)

                // Assert that matches
                assertEquals(expected, attribute)
            }
        }

        @Test
        fun `getting user attribute by userId returns null for non-existent user`() {
            transaction {
                val userAttributeDao = populateUserAttributesTable()

                // get user attribute for a non-existent user (userId = 999)
                val attribute = userAttributeDao.getUserAttributes(999)

                // Assert that no attribute is returned
                assertNull(attribute)
            }
        }
    }


    }


