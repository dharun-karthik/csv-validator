package db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

object DBConnection {
    val connection: Connection

    init {
        val url = "jdbc:postgresql://localhost:5432/csv_validator"
        connection = DriverManager.getConnection(url, "db_user", "database-user")
    }

    fun createStatement(): Statement {
        return connection.createStatement()
    }
}