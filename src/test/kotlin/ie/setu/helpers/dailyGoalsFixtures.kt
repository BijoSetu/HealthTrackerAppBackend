package ie.setu.helpers
 //import ie.setu.domain.User
 import ie.setu.domain.PayloadLogin
import ie.setu.domain.User
//import org.joda.time.DateTime
import ie.setu.domain.DailyGoal
 import org.joda.time.DateTime

val dailyGoalsTestData = arrayListOf(

    DailyGoal(
        id = 1,
        userId = 1,
        goalName = "Morning Workout",
        goalDescription = "Complete 30 minutes of cardio and 15 minutes of stretching.",
        createdAt = DateTime.now().minusDays(1),
        isCompleted = false,
        date = DateTime.now().minusDays(1),
        priority = 1,
        notes = "use the treadmill for cardio"
    ),
    DailyGoal(
        id = 2,
        userId = 1,
        goalName = "sleep for 7 hours",
        goalDescription = "sleep atleast 7 hours",
        createdAt = DateTime.now().minusDays(1),
        isCompleted = true,
        date = DateTime.now().minusDays(1),
        priority = 2,
        notes = "try to go to sleep at 10pm"
    ),
    DailyGoal(
        id = 3,
        userId = 1,
        goalName = "drink 2l of water today",
        goalDescription = "drink 2 l of water minimum in a day",
        createdAt = DateTime.now().minusDays(1),
        isCompleted = false,
        date = DateTime.now().minusDays(1),
        priority = 1,
        notes = "use the jar with litres marked"
    ),

    )