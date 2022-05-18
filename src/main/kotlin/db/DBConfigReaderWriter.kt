package db

import metaData.template.DependencyTemplate
import metaData.template.JsonConfigTemplate
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

class DBConfigReaderWriter {
    fun readConfig(configName: String): Array<JsonConfigTemplate> {
        val query = """
            SELECT config_name,
                   field_id,
                   field_name,
                   field_type,
                   is_null_allowed,
                   pattern,
                   fixed_length,
                   min_length,
                   max_length
            FROM csv_configuration
                     INNER JOIN fields f on csv_configuration.config_id = f.config_id
            where config_name LIKE ?;
        """

        val preparedStatement = DBConnection.getDBConnection().prepareStatement(query)
        preparedStatement.setString(1, configName)
        val result = preparedStatement.executeQuery()
        val finalConfig = mutableListOf<JsonConfigTemplate>()
        while (result.next()) {
            finalConfig.add(getJsonConfig(result))
        }
        return finalConfig.toTypedArray()
    }

    fun writeConfig(configName: String, jsonData: Array<JsonConfigTemplate>) {
        val queryTemplate = "INSERT INTO csv_configuration(config_name) VALUES(?) "
        val insertStatement = DBConnection.getDBConnection().prepareStatement(queryTemplate)
        insertStatement.setString(1, configName)
        insertStatement.executeUpdate()
        val preparedStatement =
            DBConnection.getDBConnection().prepareStatement("select max(config_id) as config_id from csv_configuration")
        val result = preparedStatement.executeQuery()
        if (result.next()) {
            for (data in jsonData) {
                val configId = result.getInt("config_id")
                insertFields(configId, data)
            }
        }
    }

    private fun getJsonConfig(result: ResultSet): JsonConfigTemplate {
        val fieldId = result.getInt("field_id")
        val fieldName = result.getString("field_name")
        val fieldType = result.getString("field_type")
        val isNullAllowed = result.getString("is_null_allowed")
        val pattern = result.getString("pattern")
        val fixedLength = result.getInt("fixed_length")
        val minLength = result.getInt("min_length")
        val maxLength = result.getInt("max_length")
        val values = getValues(fieldId)
        val dependencies = getDependencies(fieldId)
        return JsonConfigTemplate(
            fieldName = fieldName,
            type = fieldType,
            isNullAllowed = isNullAllowed,
            pattern = pattern,
            length = fixedLength,
            minLength = minLength,
            maxLength = maxLength,
            values = values,
            dependencies = dependencies
        )
    }

    private fun getDependencies(fieldId: Int): List<DependencyTemplate>? {
        val query =
            "SELECT dependent_on,expected_dependent_value,expected_current_value from dependencies WHERE field_id = ?"
        val preparedStatement = DBConnection.getDBConnection().prepareStatement(query)
        preparedStatement.setInt(1, fieldId)
        val result = preparedStatement.executeQuery()
        val dependencies = mutableListOf<DependencyTemplate>()
        while (result.next()) {
            val dependentOn = result.getString("dependent_on")
            val expectedDependentValue = result.getString("expected_dependent_value")
            val expectedCurrentValue = result.getString("expected_current_value")
            dependencies.add(DependencyTemplate(dependentOn, expectedDependentValue, expectedCurrentValue))
        }
        if (dependencies.isEmpty()) {
            return null
        }
        return dependencies
    }

    private fun getValues(fieldId: Int): List<String>? {
        val query = "SELECT value_name from allowed_values WHERE field_id = ?"
        val preparedStatement = DBConnection.getDBConnection().prepareStatement(query)
        preparedStatement.setInt(1, fieldId)
        val result = preparedStatement.executeQuery()
        val values = mutableListOf<String>()
        while (result.next()) {
            values.add(result.getString("value_name"))
        }
        if (values.isEmpty()) {
            return null
        }
        return values
    }

    private fun insertFields(configId: Int, jsonData: JsonConfigTemplate) {
        val insertQueryTemplate =
            "INSERT INTO fields(config_id, field_name, field_type, is_null_allowed, pattern, fixed_length, min_length, max_length) VALUES(?,?,?,?,?,?,?,?)"
        val preparedInsertStatement = DBConnection.getDBConnection().prepareStatement(insertQueryTemplate)
        setQueryFields(preparedInsertStatement, configId, jsonData)
        preparedInsertStatement.executeUpdate()
        val preparedPrimaryKeyStatement =
            DBConnection.getDBConnection().prepareStatement("select max(field_id) as field_id from fields")
        val result = preparedPrimaryKeyStatement.executeQuery()
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
        val preparedStatement = DBConnection.getDBConnection().prepareStatement(queryTemplate)
        preparedStatement.setInt(1, fieldId)
        preparedStatement.setString(2, dependency.dependentOn)
        preparedStatement.setString(3, dependency.expectedDependentFieldValue)
        preparedStatement.setString(4, dependency.expectedCurrentFieldValue)
        preparedStatement.executeUpdate()
    }

    private fun insertValues(fieldId: Int, values: List<String>?) {
        values?.forEach {
            insertValue(fieldId, it)
        }
    }

    private fun insertValue(fieldId: Int, value: String) {
        val queryTemplate = "INSERT INTO allowed_values(field_id, value_name) VALUES(?,?)"
        val preparedStatement = DBConnection.getDBConnection().prepareStatement(queryTemplate)
        preparedStatement.setInt(1, fieldId)
        preparedStatement.setString(2, value)
        preparedStatement.executeUpdate()
    }

    private fun setQueryFields(
        preparedStatement: PreparedStatement,
        configId: Int,
        jsonData: JsonConfigTemplate
    ) {
        preparedStatement.setInt(1, configId)
        preparedStatement.setString(2, jsonData.fieldName)
        preparedStatement.setString(3, jsonData.type)
        preparedStatement.setString(4, jsonData.isNullAllowed)
        preparedStatement.setString(5, jsonData.pattern)
        setInt(jsonData.length, 6, preparedStatement)
        setInt(jsonData.minLength, 7, preparedStatement)
        setInt(jsonData.maxLength, 8, preparedStatement)
    }

    private fun setInt(value: Int?, index: Int, statement: PreparedStatement) {
        if (value == null) {
            statement.setNull(index, Types.INTEGER)
        } else {
            statement.setInt(index, value)
        }
    }
}