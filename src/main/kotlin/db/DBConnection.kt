package db

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private var connection: Connection? = null

    fun initialise(link: String = "//postgres:5432/csv_validator") {
        val db = System.getenv("DB_NAME")
        val user = System.getenv("DB_USERNAME")
        val password = System.getenv("DB_PASSWORD")
        val url = "jdbc:$db:$link"
        connection = DriverManager.getConnection(url, user, password)
    }

    fun getDBConnection(): Connection {
        if (connection == null) {
            initialise()
        }
        return connection!!
    }
}