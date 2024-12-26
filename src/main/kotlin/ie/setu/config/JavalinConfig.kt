package ie.setu.config

import ie.setu.controllers.*
import io.javalin.Javalin
import ie.setu.utils.jsonObjectMapper
import io.javalin.json.JavalinJackson
import io.javalin.vue.VueComponent

class JavalinConfig{

    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.staticFiles.enableWebjars()
            it.vue.vueInstanceNameInJs = "app"
        }.apply{
                exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
                error(404) { ctx -> ctx.json("404 - Not Found") }
            }
            .start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun registerRoutes(app: Javalin) {

//        user-table-apis-for-login-register-etc

        app.post("/api/users/login",UserController::loginUser)
        app.get("/api/users", UserController::getAllUsers)
        app.get("/api/users/id/{userId}", UserController::getUserById)
        app.get("/api/users/email/{email}", UserController::getUserByEmail)
        app.post("/api/users/signup", UserController::registerNewUser)
        app.delete("/api/users/{id}", UserController::deleteUser)
        app.put("/api/users/{userId}",UserController::updateUser)

        //        daily-goals-apis-for-creating-daily-goals

        app.post("/api/users/{userId}/daily-goals", DailyGoalsController::addDailyGoalsToUser)
        app.get("/api/users/{userId}/daily-goals",DailyGoalsController:: getAllDailyGoalsByUserId)
        app.delete("/api/users/{userId}/daily-goals/{goalId}", DailyGoalsController::deleteDailyGoalsFromUser)
        app.put("/api/users/{userId}/daily-goals/{goalId}",DailyGoalsController::updateDailyGoalsForUser)

        //        daily-habits-apis-for-creating-daily-habits

         app.post("/api/users/{userId}/daily-habits", DailyHabitsController::addDailyHabitsToUser)
         app.delete("/api/users/{userId}/daily-habits/{id}", DailyHabitsController::deleteDailyHabitLog)
         app.get("/api/users/{userId}/daily-habits", DailyHabitsController::getAllDailyHabit)
        app.put("/api/users/{userId}/daily-habits/{id}", DailyHabitsController::updateDailyHabit)

        //        lifestyle-suggest-api-to-suggest-user-habitSuggestions

        app.get("/api/users/{userId}/life-style-suggestion",LifestyleSuggestionController::generateLifeStyleSuggestions)


        //    health-milestones-apis-to-track-user-milestones

        app.post("/api/users/{user-id}/milestones", MileStonesController::addMileStonesToUser)
        app.put("/api/users/{user-id}/milestones/{id}", MileStonesController::updateMileStoneOfUser)
        app.delete("/api/users/{user-id}/milestones/{id}", MileStonesController::deleteMileStoneOfUser)
        app.get("/api/users/{user-id}/milestones", MileStonesController::getAllMilesStonesOfUser)

//        vue endpoint
        app.get("/", VueComponent("<home-page></home-page>"))
        app.get("/users", VueComponent("<user-overview></user-overview>"))
        app.get("/users/{userId}", VueComponent("<user-profile></user-profile>"))
        app.get("/users/{userId}/daily-goals", VueComponent("<user-daily-goals-overview></user-daily-goals-overview>"))
        app.get("/login-or-signup", VueComponent("<login-or-signup></login-or-signup>"))



    }

    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7000
    }
}
