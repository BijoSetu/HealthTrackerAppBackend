package ie.setu.helpers

 //import ie.setu.domain.User
import ie.setu.domain.DailyGoal
import ie.setu.domain.PayloadLogin
import ie.setu.domain.User
//import org.joda.time.DateTime


val nonExistingEmail = "112233445566778testUser@xxxxx.xx"
val validName = "Test User 1"
val validEmail = "testuser1@test.com"
val validPassword = "password@123"
val users = arrayListOf<User>(
    User(name = "Alice Wonderland", email = "alice@wonderland.com", userid = 1, password = "password@123"),
    User(name = "Bob Cat", email = "bob@cat.ie", userid = 2, password = "password"),
    User(name = "Mary Contrary", email = "mary@contrary.com",  userid = 3, password = "password"),
    User(name = "Carol Singer", email = "carol@singer.com",  userid = 4, password = "password")
)

val payloadLogin = arrayListOf<PayloadLogin>(

    PayloadLogin(email = "alice@wonderland.com", password = "password@123"),
)
