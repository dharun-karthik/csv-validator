package db

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private val connection: Connection

    init {
        val url = "jdbc:postgresql://localhost:5432/csv_validator"
        connection = DriverManager.getConnection(url, "db_user", "database-user")
    }

    private fun getFieldValues(connection: Connection) {
        val statement = connection.createStatement()
        val result = statement.executeQuery("SELECT * FROM fields")
        while (result.next()) {
            println(result.getString("field_name"))
        }
    }
}