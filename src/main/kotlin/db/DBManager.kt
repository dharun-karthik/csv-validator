package db

import java.sql.DriverManager

class DBConnection {
    fun connect(){
        val url = "jdbc:postgresql://localhost:5432/csv_validator"
        val connection = DriverManager.getConnection(url,"db_user","database-user")
        val statement = connection.createStatement()
        val result = statement.executeQuery("SELECT * FROM fields")
        while(result.next()){
            println(result.getString("field_name"))
        }
    }
}