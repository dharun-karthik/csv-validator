package db

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private var connection: Connection? = null

    fun initialise(url: String = "jdbc:postgresql://localhost:5432/csv_validator") {
        connection = DriverManager.getConnection(url, "db_user", "database-user")
    }

    fun getDBConnection(): Connection {
        if (connection == null) {
            initialise()
        }
        return connection!!
    }
}