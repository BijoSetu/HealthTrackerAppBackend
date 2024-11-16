package ie.setu.domain

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime

data class Milestone(
    val id: Int,
    val userId: Int,
    @JsonProperty("milestone_name")
    val milestone: String,
    @JsonProperty("achieved_date")
    val achievedDate: DateTime?,
    val notes: String?,
    val created: DateTime?,
    val updated: DateTime?,
)