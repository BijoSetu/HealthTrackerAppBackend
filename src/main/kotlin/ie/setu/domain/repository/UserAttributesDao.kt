package ie.setu.domain.repository

import ie.setu.domain.db.UserAttributes
import ie.setu.domain.UserAttribute
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Manages the database transactions and returns the results of the transactions for user attributes.
 */
class UserAttributesDao {

    // Add user attributes
    fun addUserAttribute(userAttribute: UserAttribute) {
        return transaction {
            UserAttributes.insert {
                it[userId] = userAttribute.userId
                it[age] = userAttribute.age
                it[gender] = userAttribute.gender
                it[weight] = userAttribute.weight
                it[height] = userAttribute.height
            }
        }
    }

    // Update user attributes
    fun updateUserAttribute(userId: Int, userAttribute: UserAttribute): Int {
        return transaction {
            UserAttributes.update({ UserAttributes.userId eq userId }) {
                it[age] = userAttribute.age
                it[gender] = userAttribute.gender
                it[weight] = userAttribute.weight
                it[height] = userAttribute.height
            }
        }
    }

    // Delete user attributes
    fun deleteUserAttribute(userId: Int): Int {
        return transaction {
            UserAttributes.deleteWhere { UserAttributes.userId eq userId }
        }
    }

    // Get user attributes by userId
    fun getUserAttributes(userId: Int): UserAttribute? {
        return transaction {
            UserAttributes.selectAll().where { UserAttributes.userId eq userId }
                .map {
                    UserAttribute(
                        id = it[UserAttributes.id],
                        userId = it[UserAttributes.userId],
                        age = it[UserAttributes.age],
                        gender = it[UserAttributes.gender],
                        weight = it[UserAttributes.weight],
                        height = it[UserAttributes.height]
                    )
                }
                .firstOrNull()
        }
    }
}
