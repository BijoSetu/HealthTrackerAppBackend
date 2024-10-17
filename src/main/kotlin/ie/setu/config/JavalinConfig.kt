package ie.setu.config

import io.javalin.Javalin
import ie.setu.controllers.HealthTrackerController

class JavalinConfig{
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else 7001
    }
    fun startJavalinService():Javalin {

        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
        }.start(getRemoteAssignedPort())

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
        app.get("/api/users", HealthTrackerController::getAllUsers)
        app.get("/api/users/{user-id}", HealthTrackerController::getUserByUserId)
    }
}
