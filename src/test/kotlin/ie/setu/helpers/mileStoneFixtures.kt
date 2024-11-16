package ie.setu.helpers

import ie.setu.domain.Milestone
import org.joda.time.DateTime

val mileStoneFixtures = arrayListOf(
    Milestone(
        id = 1,
        userId = 1,
        milestone = "Walked 100,000 Steps",
        achievedDate =DateTime.parse("2024-11-01T09:00:00"),
        notes = "Achieved this milestone in 10 days of consistent walking.",
        created = DateTime.parse("2024-11-01T09:00:00"),
        updated = DateTime.parse("2024-11-10T10:05:00")
    ),
    Milestone(
        id = 2,
        userId = 1,
        milestone = "Completed 30-Day Yoga Challenge",
        achievedDate = DateTime.parse("2024-10-25T07:00:00"),
        notes = "Successfully completed 30 consecutive days of yoga practice.",
        created = DateTime.parse("2024-09-15T08:30:00"),
        updated = DateTime.parse("2024-10-25T07:10:00")
    ),
    Milestone(
        id = 3,
        userId = 1,
        milestone = "Lost 5 Kg in 3 Months",
        achievedDate = DateTime.parse("2024-11-05T12:00:00"),
        notes = "Achieved weight loss goal through consistent exercise and diet.",
        created = DateTime.parse("2024-08-01T10:00:00"),
        updated = DateTime.parse("2024-11-05T12:05:00")
    ),
    Milestone(
        id = 4,
        userId = 1,
        milestone = "Ran a Half Marathon",
        achievedDate = DateTime.parse("2024-11-01T06:00:00"),
        notes = "Completed my first half marathon in 2 hours and 15 minutes.",
        created = DateTime.parse("2024-07-01T10:00:00"),
        updated = DateTime.parse("2024-11-01T06:10:00")
    ),
    Milestone(
        id = 5,
        userId = 1,
        milestone = "Read 20 Books in a Year",
        achievedDate = DateTime.parse("2024-12-31T18:00:00"),
        notes = "Finished my 20th book of the year, achieving my reading goal.",
        created = DateTime.parse("2024-01-01T09:00:00"),
        updated = DateTime.parse("2024-12-31T18:05:00")
    )
)