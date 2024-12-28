package ie.setu.controllers.controllerComponents

import io.javalin.http.Context

//check to see if user id is null or not before doing api calls
public fun validateUserId(ctx:Context,userId:Int?):Boolean{

    if (userId == null) {

        ctx.json(mapOf("error" to "invalid user id"))
        return false
    }
return true
}

//check to see if userid and id of the object being passed is null or not before doing api calls
public fun validateUserIdAndId(ctx:Context,userId:Int?,id:Int?):Boolean{

    if (userId == null || id == null) {

        ctx.json(mapOf("error" to "invalid user id or Id"))
        return false
    }
    return true

}

//send the appropriate response upon api call returns
public fun sendResponse(ctx: Context, successCondition: Any, successOn: String, errorOn: String) {
    when (successCondition) {
        is Boolean -> {
             if (successCondition) {
                ctx.status(200).json(mapOf("success" to "Successfully $successOn"))
            } else {
                 ctx.status(400).json(mapOf("error" to errorOn))
            }
        }
        is Int -> {
             if (successCondition > 0) {
                 ctx.status(200).json(mapOf("success" to "Successfully $successOn"))
            } else {
                 ctx.status(400).json(mapOf("error" to errorOn))
            }
        }
        else -> {
            ctx.status(400).json(mapOf("error" to "Unexpected success condition"))
        }
    }}
