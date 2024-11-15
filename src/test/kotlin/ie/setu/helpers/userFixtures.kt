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
