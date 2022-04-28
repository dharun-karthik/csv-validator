package metaData

import metaData.csv.CsvSplitter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CsvSplitterTest {

    @Test
    fun shouldGetAllValues() {
        val csvSplitter = CsvSplitter("'hello,dude',,dino thunder,\"mystic force,jungle fury\"")

        val expected = listOf("hello,dude", "null", "dino thunder", "mystic force,jungle fury")
        val actual = csvSplitter.getAllValues()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetEmptyListWhenThereIsNoContent() {
        val csvSplitter = CsvSplitter("")

        val expected = listOf<String>()
        val actual = csvSplitter.getAllValues()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNextValue() {
        val csvSplitter = CsvSplitter("'hello,dude',,dino thunder,\"mystic force,jungle fury\"")

        val expected = "hello,dude"
        val actual = csvSplitter.getNextValue()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNullValueIfEmptyValueIsThere() {
        val csvSplitter = CsvSplitter("'hello,dude',,dino thunder,\"mystic force,jungle fury\"")
        csvSplitter.getNextValue()

        val expected = "null"
        val actual = csvSplitter.getNextValue()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldGetNullValueIfNoValueIsThere() {
        val csvSplitter = CsvSplitter("")

        val expected = "null"
        val actual = csvSplitter.getNextValue()

        assertEquals(expected, actual)
    }
}