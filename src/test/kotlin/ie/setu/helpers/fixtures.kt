package ie.setu.helpers
//import ie.setu.domain.User
import ie.setu.domain.DailyGoal
import ie.setu.domain.PayloadLogin
import ie.setu.domain.User
//import org.joda.time.DateTime


val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"

val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", user_id = 1, password = "password@123"),
    User(name = "Bob Cat", email = "bob@cat.ie", user_id = 2, password = "password"),
    User(name = "Mary Contrary", email = "mary@contrary.com",  user_id = 3, password = "password"),
    User(name = "Carol Singer", email = "carol@singer.com",  user_id = 4, password = "password")
)

val payloadLogin = arrayListOf<PayloadLogin>(

    PayloadLogin(email = "alice@wonderland.com", password = "password@123"),
)

val dailyGoalsTestData = arrayListOf(
    DailyGoal(
        id = 1,
        userId = 1,
        goalName = "Morning Workout",
        goalDescription = "Complete 30 minutes of cardio and 15 minutes of stretching.",
//        createdAt = DateTime.now().minusDays(1),
        isCompleted = false,
//        date = DateTime.now().minusDays(1),
        priority = 1,
        notes = "use the treadmill for cardio"
    ),
    DailyGoal(
        id = 2,
        userId = 1,
        goalName = "sleep for 7 hours",
        goalDescription = "sleep atleast 7 hours",

        isCompleted = true,

        priority = 2,
        notes = "try to go to sleep at 10pm"
    ),
    DailyGoal(
        id = 3,
        userId = 1,
        goalName = "drink 2l of water today",
        goalDescription = "drink 2 l of water minimum in a day",

        isCompleted = false,

        priority = 1,
        notes = "use the jar with litres marked"
    ),

)