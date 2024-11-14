package ie.setu.domain.repository
import ie.setu.domain.PayloadLogin
import ie.setu.domain.db.Users
import ie.setu.domain.User
import org.jetbrains.exposed.sql.transactions.transaction
import ie.setu.utils.mapToUser
import jdk.internal.org.objectweb.asm.util.Printer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.sql.SQLException

class UserDao{

//    Create a new user if not already exists
fun registerNewUser(user: User): Boolean {
    return transaction {
        val existingUser = Users.selectAll().where { Users.email eq user.email }.singleOrNull()
        if (existingUser != null) {
            false
        } else {
            Users.insert {
                it[name] = user.name
                it[email] = user.email
                it[password] = user.password
            }
            true
        }
    }
}

    fun getAll(): List<User> {
             val userList: ArrayList<User> = arrayListOf()
             transaction {
                 Users.selectAll().map {
                     userList.add(mapToUser(it)) }
             }
             return userList
         }
//logging in a user by checking the email and password has a matching user in the database

    fun loginUser(payloadLogin: PayloadLogin):Boolean{
        return try {
     transaction {
//         took this line from chatgpt as part of troubleshooting to find the syntax
        Users.selectAll().where { Users.email eq payloadLogin.email and (Users.password eq payloadLogin.password) }
            .singleOrNull() != null
    }
}catch (e: Exception){
     println(e.toString())
            return false
}catch (e:SQLException){

    println(e.toString())
            return false
}

    }

    fun getUserById(id: Int):User?{
        return transaction {
            Users.selectAll().where { Users.user_id eq id }
                .map{mapToUser(it)}
                .firstOrNull()
        }
    }

      fun save(user: User){
          transaction {
              Users.insert {
                  it[name] = user.name
                  it[email] = user.email
                  it[password] = user.password
              }
          }
    }

    fun findByEmail(email :String):User?{return transaction {
        Users.selectAll().where { Users.email eq email}
            .map{mapToUser(it)}
            .firstOrNull()
    }}

//delete a user from db
    fun deleteUser(id: Int):Int {  return transaction{
        Users.deleteWhere{ Users.user_id eq id }
    }}

//update option for user to update the name ,email and password
         fun updateUser(id: Int, user:PayloadLogin):Int{
            return  transaction {
                 Users.update ({
                     Users.user_id eq id}) {

                     it[email] = user.email
                     it[password] = user.password
                 }
             }
         }
     }

