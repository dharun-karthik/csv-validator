package validation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConfigJsonValidatorTest {
    private val configJsonValidator = ConfigJsonValidator()

    @Test
    fun shouldGetConfigErrors() {
        val content = """[{"minLength":"-88"}]"""

        val expected =
            """[{"1":[{"Field errors":["Field 'fieldName' should be provided","Field 'type' should be provided","Min length should be greater than 0"]}]}]"""

        val actual = configJsonValidator.validate(content)

        assertEquals(expected, actual.toString())
    }

    @Test
    fun shouldGetConfigDependencyErrors() {
        val content = """[
            |{
                |"fieldName":"name",
                |"type":"number",
                |"dependencies":
                    |[
                        |{
                            |"dependentOn":"on",
                            |"expectedCurrentFieldValue":"current"
                        |}
                    |]
            |}
        |]""".trimMargin()

        val expected =
            """[{"1":[{"Dependency errors":[{"1":["Dependency field 'expectedDependentFieldValue' should be present"]}]}]}]"""

        val actual = configJsonValidator.validate(content)

        assertEquals(expected, actual.toString())
    }
    @Test
    fun shouldNotGetErrorsWhenEveryValueIsPassed() {
        val content = """[
            |{
                |"fieldName":"name",
                |"type":"number",
                |"dependencies":
                    |[
                        |{
                            |"dependentOn":"on",
                            |"expectedCurrentFieldValue":"current",
                            |"expectedDependentFieldValue":"depend"
                        |}
                    |]
            |}
        |]""".trimMargin()

        val expected =
            """[]"""

        val actual = configJsonValidator.validate(content)

        assertEquals(expected, actual.toString())
    }
    @Test
    fun shouldNotGetErrorsWhenNoValuesArePassed() {
        val content = """[]""".trimMargin()

        val expected =
            """[]"""

        val actual = configJsonValidator.validate(content)

        assertEquals(expected, actual.toString())
    }
}