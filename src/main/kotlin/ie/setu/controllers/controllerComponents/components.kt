package ie.setu.controllers.controllerComponents

import io.javalin.http.Context

public fun validateUserId(ctx:Context,userId:Int?):Boolean{

    if (userId == null) {

        ctx.json(mapOf("error" to "invalid user id"))
        return false
    }
return true
}

public fun validateUserIdAndId(ctx:Context,userId:Int?,id:Int?):Boolean{

    if (userId == null || id == null) {

        ctx.json(mapOf("error" to "invalid user id or Id"))
        return false
    }
    return true

}

public fun sendResponse(ctx: Context, successCondition: Any, successOn: String, errorOn: String) {
    when (successCondition) {
        is Boolean -> {
             if (successCondition) {
                ctx.json(mapOf("success" to "Successfully $successOn"))
            } else {
                ctx.json(mapOf("error" to errorOn))
            }
        }
        is Int -> {
             if (successCondition > 0) {
                ctx.json(mapOf("success" to "Successfully $successOn"))
            } else {
                ctx.json(mapOf("error" to errorOn))
            }
        }
        else -> {
             ctx.json(mapOf("error" to "Unexpected success condition"))
        }
    }}
