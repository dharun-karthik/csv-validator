package db

import java.io.File

class DatabaseSetUp {
    fun createTables() {
        val query = getQueryFromFile()
        val connection = DBConnection.getDBConnection()
        val statement = connection.createStatement()
        statement.execute(query)
    }

    private fun getQueryFromFile(): String {
        val queryFile = File("database/create_fields_table.sql")
        return queryFile.readText()
    }
}
