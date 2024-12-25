package ie.setu.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime

data class Milestone(
    val id: Int,
    val userId: Int,
    val milestone: String,
    val achievedDate: DateTime?,
    val notes: String?,
    val created: DateTime?,
    val updated: DateTime?,
)