package ie.setu.config

import ie.setu.controllers.*
import io.javalin.Javalin
import ie.setu.utils.jsonObjectMapper
import io.javalin.json.JavalinJackson

class JavalinConfig{
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7002
    }
    fun startJavalinService():Javalin {


        val app = Javalin.create {
//            this line is taken from chatgpt to globally set the jsonObjectMapper for serialization and deserialization
            config ->
            config.jsonMapper(JavalinJackson(jsonObjectMapper()))
        }.apply {
            exception(Exception::class.java) { e, ctx ->
                e.printStackTrace()
                ctx.status(500).json("Internal Server Error")
            }
            error(404) { ctx -> ctx.json("404 - Not Found") }
        }.start(getRemoteAssignedPort())

//        val app = Javalin.create().apply {
//
//            exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
//            error(404) { ctx -> ctx.json("404 - Not Found") }
//        }.start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }


//        val app = Javalin.create().apply{exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
//            error(404) { ctx -> ctx.json("404 - Not Found") }
//        }.start(7001)
//
//registerRoutes(app)
//        return app
//    }

    private fun registerRoutes(app: Javalin) {

//        user-table-apis-for-login-register-etc

        app.post("/api/users/login",UserController::loginUser)
        app.get("/api/users", UserController::getAllUsers)
        app.get("/api/users/{user-id}", UserController::getUserById)
        app.post("/api/users/signup", UserController::registerNewUser)
        app.delete("/api/users/{user-id}", UserController::deleteUser)
        app.put("/api/users/{user-id}",UserController::updateUser)

        //        daily-goals-apis-for-creating-daily-goals

        app.post("/api/users/{user-id}/daily-goals", DailyGoalsController::addDailyGoalsToUser)
        app.get("/api/users/{user-id}/daily-goals",DailyGoalsController:: getAllDailyGoalsByUserId)
        app.delete("/api/users/{user-id}/daily-goals/{goal-id}", DailyGoalsController::deleteDailyGoalsFromUser)
        app.put("/api/users/{user-id}/daily-goals/{goal-id}",DailyGoalsController::updateDailyGoalsForUser)

        //        daily-habits-apis-for-creating-daily-habits

         app.post("/api/users/{user-id}/daily-habits", DailyHabitsController::addDailyHabitsToUser)
         app.delete("/api/users/{user-id}/daily-habits/{id}", DailyHabitsController::deleteDailyHabitLog)
         app.get("/api/users/{user-id}/daily-habits", DailyHabitsController::getAllDailyHabit)
        app.put("/api/users/{user-id}/daily-habits/{id}", DailyHabitsController::updateDailyHabit)

        //        lifestyle-suggest-api-to-suggest-user-habitSuggestions

        app.get("/api/users/{user-id}/life-style-suggestion",LifestyleSuggestionController::generateLifeStyleSuggestions)


        //    health-milestones-apis-to-track-user-milestones

        app.post("/api/users/{user-id}/milestones", MileStonesController::addMileStonesToUser)
        app.put("/api/users/{user-id}/milestones/{id}", MileStonesController::updateMileStoneOfUser)
        app.delete("/api/users/{user-id}/milestones/{id}", MileStonesController::deleteMileStoneOfUser)
        app.get("/api/users/{user-id}/milestones", MileStonesController::getAllMilesStonesOfUser)

    }
}
