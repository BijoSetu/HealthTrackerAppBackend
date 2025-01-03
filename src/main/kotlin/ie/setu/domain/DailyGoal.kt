package ie.setu.domain

import org.joda.time.DateTime


data class DailyGoal(
    val id: Int?,
    var userId: Int,
    val goalName: String,
    val goalDescription: String,
    val createdAt: DateTime,
    val isCompleted: Boolean = false,
    val date: DateTime,
    val priority: Int,
    val notes: String
)