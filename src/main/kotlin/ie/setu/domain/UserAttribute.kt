package ie.setu.domain

data class UserAttribute(
    val id: Int,
    val userId: Int,
    val age: Int,
    val gender: String,
    val weight: Float,
    val height: Float
)
