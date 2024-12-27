package ie.setu.domain.repository

import ie.setu.domain.Milestone
import ie.setu.domain.db.MileStones
import ie.setu.utils.mapToMileStones
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class MileStonesDAO {


    fun addNewMileStone(milestone: Milestone) {
        return transaction {
            MileStones.insert {
                it[userId] = milestone.userId
                it[milestoneName] = milestone.milestoneName
                it[achievedDate] = milestone.achievedDate ?: DateTime.now()
                it[notes] = milestone.notes
                it[createdAt] = milestone.created ?: DateTime.now()
                it[updatedAt] = milestone.updated ?: DateTime.now()
            }
        }
    }

    fun getMilestonesByUserId(userId: Int): List<Milestone> {
        return transaction {
            MileStones.selectAll().where { MileStones.userId eq userId }
                .map {
                   mapToMileStones(it)
                }
        }
    }

    fun updateMilestone(id:Int,userid: Int,milestone: Milestone): Int {
        return transaction {
            MileStones.update({(MileStones.userId eq userid) and (MileStones.id eq id) }) {
                it[milestoneName] = milestone.milestoneName
                it[achievedDate] = milestone.achievedDate ?: DateTime.now()
                it[notes] = milestone.notes
                it[updatedAt] = DateTime.now()
            }
        }
    }

    fun deleteMilestone(userid: Int, milestoneId: Int): Int {
        return transaction {
            MileStones.deleteWhere { (userId eq userid) and (id eq milestoneId) }
        }
    }


}