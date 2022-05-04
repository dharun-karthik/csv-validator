package db

import com.google.gson.Gson
import metaData.template.DependencyTemplate
import metaData.template.JsonConfigTemplate
import java.sql.PreparedStatement
import java.sql.Types

class ConfigReaderWriter {
    private val gson = Gson()

    fun readConfig(configName: String): Array<JsonConfigTemplate> {
        val statement = DBConnection.createStatement()
        val result = statement.executeQuery("SELECT * FROM fields")
        while (result.next()) {
            println(result.getString("field_name"))
        }
        val data = ""
        return gson.fromJson(data, Array<JsonConfigTemplate>::class.java) ?: return arrayOf()
    }

    fun addConfig(configName: String, jsonData: Array<JsonConfigTemplate>) {
        val queryTemplate = "INSERT INTO csv_configuration(config_name) VALUES(?) RETURNING config_id"
        val statement = DBConnection.connection.prepareStatement(queryTemplate)
        statement.setString(1, configName)
        val result = statement.executeQuery()
        if (result.next()) {
            for (data in jsonData) {
                val configId = result.getInt("config_id")
                insertFields(configId, data)
            }
        }
    }

    private fun insertFields(configId: Int, jsonData: JsonConfigTemplate) {
        val queryTemplate =
            "INSERT INTO fields(config_id, field_name, field_type, is_null_allowed, pattern, fixed_length, min_length, max_length) VALUES(?,?,?,?,?,?,?,?) RETURNING field_id"
        val statement = DBConnection.connection.prepareStatement(queryTemplate)
        setQueryFields(statement, configId, jsonData)
        val result = statement.executeQuery()
        if (result.next()) {
            val fieldId = result.getInt("field_id")
            insertValues(fieldId, jsonData.values)
            insertDependencies(fieldId, jsonData.dependencies)
        }
    }

    private fun insertDependencies(fieldId: Int, dependencies: List<DependencyTemplate>?) {
        dependencies?.forEach {
            insertDependency(fieldId, it)
        }
    }

    private fun insertDependency(fieldId: Int, dependency: DependencyTemplate) {
        val queryTemplate =
            "INSERT INTO dependencies(field_id, dependent_on, expected_dependent_value, expected_current_value) VALUES(?,?,?,?)"
        val statement = DBConnection.connection.prepareStatement(queryTemplate)
        statement.setInt(1, fieldId)
        statement.setString(2, dependency.dependentOn)
        statement.setString(3, dependency.expectedDependentFieldValue)
        statement.setString(4, dependency.expectedCurrentFieldValue)
         statement.executeUpdate()
    }

    private fun insertValues(fieldId: Int, values: List<String>?) {
        values?.forEach {
            insertValue(fieldId, it)
        }
    }

    private fun insertValue(fieldId: Int, value: String) {
        val queryTemplate = "INSERT INTO values(field_id, value_name) VALUES(?,?)"
        val statement = DBConnection.connection.prepareStatement(queryTemplate)
        statement.setInt(1, fieldId)
        statement.setString(2, value)
        statement.executeUpdate()
    }

    private fun setQueryFields(
        statement: PreparedStatement,
        configId: Int,
        jsonData: JsonConfigTemplate
    ) {
        statement.setInt(1, configId)
        statement.setString(2, jsonData.fieldName)
        statement.setString(3, jsonData.type)
        statement.setString(4, jsonData.isNullAllowed)
        statement.setString(5, jsonData.pattern)
        if (jsonData.length == null) {
            statement.setNull(6, Types.INTEGER)
        } else {
            statement.setInt(6, jsonData.length)
        }
        if (jsonData.minLength == null) {
            statement.setNull(7, Types.INTEGER)
        } else {
            statement.setInt(7, jsonData.minLength)
        }
        if (jsonData.maxLength == null) {
            statement.setNull(8, Types.INTEGER)
        } else {
            statement.setInt(8, jsonData.maxLength)
        }
    }
}