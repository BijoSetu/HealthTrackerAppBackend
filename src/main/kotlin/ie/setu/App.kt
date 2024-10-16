package ie.setu

import ie.setu.config.JavalinConfig
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import ie.setu.config.DbConfig


fun main(args: Array<String>) {

    DbConfig().getDbConnection()
    JavalinConfig().startJavalinService()

}