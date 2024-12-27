package ie.setu.domain

import org.joda.time.DateTime

data class Milestone(
    val id: Int,
    val userId: Int,
    val milestoneName: String,
    val achievedDate: DateTime?,
    val notes: String?,
    val created: DateTime?,
    val updated: DateTime?,
)