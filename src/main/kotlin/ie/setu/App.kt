package ie.setu

import ie.setu.config.JavalinConfig
import ie.setu.config.DbConfig


fun main(args: Array<String>) {

    DbConfig().getDbConnection()
    JavalinConfig().startJavalinService()
}

