package validation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConfigJsonValidatorTest {
    private val configJsonValidator = ConfigJsonValidator()

    @Test
    fun shouldGetConfigErrors() {
        val content = """[{"minLength":"-88"}]"""

        val expected =
            """[{"1":[{"Field errors":["Field 'fieldName' should be provided","Field 'type' should be provided","<empty> is not supported","Min length should be greater than 0"]}]}]"""

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
                            |"dependentOn":"Product",
                            |"expectedCurrentFieldValue":"current"
                        |}
                    |]
            |}
        |]""".trimMargin()

        val expected =
            """[{"1":[{"Dependency errors":[{"1":["Dependency field 'expectedDependentFieldValue' should be present"]}]},{"Field errors":["Field name Product not found"]}]}]"""

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
                            |"dependentOn":"product",
                            |"expectedCurrentFieldValue":"current",
                            |"expectedDependentFieldValue":"depend"
                        |}
                    |]
            |},
            |{
                |"fieldName":"product",
                |"type":"text"
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

    @Test
    fun shouldGetInvalidKeyErrors() {
        val content = """[
            |{
                |"fieldName":"name",
                |"type":"number",
                |"abc":"1"
            |}
        |]""".trimMargin()

        val expected =
            """[{"1":[{"Field errors":["abc is not a valid key"]}]}]"""

        val actual = configJsonValidator.validate(content)

        assertEquals(expected, actual.toString())
    }
}