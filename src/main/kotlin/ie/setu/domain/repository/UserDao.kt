package ie.setu.domain.repository
import ie.setu.domain.User
import ie.setu.domain.db.Users
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.selectAll
import ie.setu.utils.mapToUser

class UserDao{


//     val users = arrayListOf<User>(
//        User( id = 1,name = "Alice", email = "alice@alice.kt"),
//        User( id = 1,name = "Mark", email = "Mark @kt"),
//                User( id = 1,name = "Dean", email = "Dean@kt"),
//    User( id = 4,name = "Jack", email = "jack@kt")
//    )

         fun getAll(): ArrayList<User> {
             val userList: ArrayList<User> = arrayListOf()
             transaction {
                 Users.selectAll().map {
                     userList.add(mapToUser(it)) }
             }
             return userList
         }
    fun findById(id: Int): User?{
        return null
    }

      fun save(user: User){

    }

    fun findByEmailId(id:Int): User?{return null}


    fun delete(id: Int){}
     fun update(id: Int, user: User){
    }
}