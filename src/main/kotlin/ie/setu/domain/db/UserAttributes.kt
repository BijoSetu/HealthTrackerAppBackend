package ie.setu.domain.db
import org.jetbrains.exposed.sql.Table



object UserAttributes : Table("user_attributes") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id")
    val age = integer("age")
    val gender = varchar("gender", 10)
    val weight = float("weight")
    val height = float("height")

    override val primaryKey = PrimaryKey(id, name = "PK_UserAttributes_ID")
}
