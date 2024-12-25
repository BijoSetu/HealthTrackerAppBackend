package ie.setu.domain

data class User(val userid:Int,val name:String, val email:String, val password:String)


data class PayloadLogin(
    val email: String,
    val password: String
)