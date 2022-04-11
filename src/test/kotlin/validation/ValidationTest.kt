package validation

import metaData.MetaDataReaderWriter
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ValidationTest {
    @Test
    fun shouldGiveLengthErrorLinesAsResult() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val validation = Validation(metaDataReaderWriter)
        val csvData = """[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59",
        "Export": "N",
        "Country Name": "null",
        "Source City": "Nagpur",
        "Country Code": "null",
        "Source Pincode": "440001"
    },
    {
        "Product Id": "1234",
        "Product Description": "Chairs",
        "Price": "1000",
        "Export": "Y",
        "Country Name": "AUS",
        "Source City": "Mumbai",
        "Country Code": "61",
        "Source Pincode": "400001"
    }
]"""
        val jsonCsvData = JSONArray(csvData)
        val expected = JSONArray()
        expected.put(JSONObject().put("1", "Length Error in Product Id"))
        expected.put(JSONObject().put("1", "Length Error in Country Code"))
        expected.put(JSONObject().put("1", "Length Error in Product Description"))
        expected.put(JSONObject().put("2", "Length Error in Product Id"))
        expected.put(JSONObject().put("2", "Length Error in Product Description"))
        val result = validation.lengthValidation(jsonCsvData)

        Assertions.assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun shouldGiveTypeErrorLinesAsResult() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val validation = Validation(metaDataReaderWriter)
        val csvData = """[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59d",
        "Export": "N",
        "Source City": "Nagpur",
        "Source Pincode": "440001"
    },
    {
        "Product Id": "1234",
        "Product Description": "Chairs",
        "Price": "1000",
        "Export": "Y",
        "Country Name": "AUS",
        "Source City": "Mumbai",
        "Country Code": "61",
        "Source Pincode": "400001"
    }
]"""
        val jsonCsvData = JSONArray(csvData)

        val expected = JSONArray()
        expected.put(JSONObject().put("1", "Type Error in Price"))
        val result = validation.typeValidation(jsonCsvData)

        Assertions.assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun shouldGiveDependencyErrorLinesAsResult() {
        val metaDataReaderWriter = MetaDataReaderWriter("src/test/kotlin/metaDataTestFiles/csv-meta-data-test.json")
        val validation = Validation(metaDataReaderWriter)
        val csvData = """[
    {
        "Product Id": "1564",
        "Product Description": "Table",
        "Price": "4500.59",
        "Export": "N",
        "Country Name":"AUS",
        "Source City": "Nagpur",
        "Country Code": "null",
        "Source Pincode": "440001"
    },
    {
        "Product Id": "1234",
        "Product Description": "Chairs",
        "Price": "1000",
        "Export": "Y",
        "Country Name": "AUS",
        "Source City": "Mumbai",
        "Country Code": "61",
        "Source Pincode": "400001"
    }
]"""
        val jsonCsvData = JSONArray(csvData)
        val expected = JSONArray()
        expected.put(JSONObject().put("1", "Dependency Error in Country Name"))
        expected.put(JSONObject().put("1", "Dependency Error in Country Code"))
        val result = validation.dependencyValidation(jsonCsvData)

        Assertions.assertEquals(expected.toString(), result.toString())
    }

}