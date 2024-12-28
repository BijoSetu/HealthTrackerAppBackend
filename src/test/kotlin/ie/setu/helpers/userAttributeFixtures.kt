package ie.setu.helpers

import ie.setu.domain.UserAttribute


val userAttributesFixtures = arrayListOf(
    UserAttribute(
        id = 1,
        userId = 1,
        age = 25,
        gender = "Male",
        weight = 70.5f,
        height = 175.0f
    ),
    UserAttribute(
        id = 2,
        userId = 1,
        age = 30,
        gender = "Female",
        weight = 60.0f,
        height = 165.0f
    ),
    UserAttribute(
        id = 3,
        userId = 2,
        age = 22,
        gender = "Male",
        weight = 80.0f,
        height = 180.0f
    ),
    UserAttribute(
        id = 4,
        userId = 3,
        age = 27,
        gender = "Female",
        weight = 65.0f,
        height = 160.0f
    )
)
