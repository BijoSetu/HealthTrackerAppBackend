package ie.setu.domain.repository
import ie.setu.domain.User
class UserDao{


     val users = arrayListOf<User>(

        User(name = "Alice", id = 1, email = "alice@alice.kt"),
        User(name = "Mark", id =2, email = "Mark @kt"),
                User(name = "Dean", id = 3, email = "Dean@kt"),
    User(name = "Jack", id = 4, email = "jack@kt")
    )

    fun getAllUsers():ArrayList<User>{

        return users
    }
    fun findById(id: Int): User?{
        return users.find {it.id == id}
    }

      fun save(user: User){
        users.add(user)
    }

}