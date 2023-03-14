package db

import java.sql.Connection
import java.sql.DriverManager

object DBConnection {
    private var connection: Connection? = null

    private fun initialise() {
        val url = getDbUrl()
        println(url)
        initialise(url)
    }

    fun initialise(url: String) {
        val user = System.getenv("DB_USERNAME")
        val password = System.getenv("DB_PASSWORD")
        connection = DriverManager.getConnection(url, user, password)
    }

    private fun getDbUrl(): String {
        val dbUrl = System.getenv("DB_URL")
        val db = System.getenv("DB")
        val dbName = System.getenv("DB_NAME")
        return "jdbc:$db://$dbUrl/$dbName"
    }

    fun getDBConnection(): Connection {
        if (connection == null) {
            initialise()
        }
        return connection!!
    }
}