package ie.setu.config

import org.jetbrains.exposed.sql.Database
import org.postgresql.util.PSQLException
import io.github.oshai.kotlinlogging.KotlinLogging


class DbConfig {
    private val logger=KotlinLogging.logger {}
    private lateinit var dbConfig: Database

    fun getDbConnection(): Database {

        val PGHOST= "dpg-ctjjkh8gph6c738hk0eg-a.frankfurt-postgres.render.com"
        val PGPORT = "5432"
        val PGUSER = "healthtrackerapp_user"
        val PGPASSWORD = "23xwwqTffOm2xXDZt8YkVUH04XDtp1qo"
        val PGDATABASE = "healthtrackerapp"


        //url format should be jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        try {
logger.info { "Connecting to Db ----- $dbUrl" }
            dbConfig = Database.connect(
                url = dbUrl, driver = "org.postgresql.Driver",
                user = PGUSER, password = PGPASSWORD
            )
logger.info { "Connected to Db ----- ${dbConfig.url}" }
        } catch (e: PSQLException) {
            logger.info { "Error connecting  to Db ----- $e" }
        }
        return dbConfig

    }
}