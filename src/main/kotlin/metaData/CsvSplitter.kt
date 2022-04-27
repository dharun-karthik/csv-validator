package metaData

class CsvSplitter(line: String) {

    private val splitContent: Sequence<MatchResult>
    private val contentIterator: Iterator<MatchResult>

    init {
        val expression =
            """(?!\s*${'$'})\s*(?:'([^'\\]*(?:\\[\S\s][^'\\]*)*)'|"([^"\\]*(?:\\[\S\s][^"\\]*)*)"|([^,'"\s\\]*(?:\s+[^,'"\s\\]+)*))\s*(?:,|${'$'})"""
        val regex = expression.toRegex()
        splitContent = regex.findAll(line)
        contentIterator = splitContent.iterator()
    }

    fun getAllValues(): List<String> {
        val listOfValues = mutableListOf<String>()
        splitContent.forEach { matchResult ->
            val rawValue = matchResult.groupValues
            val value = getValueFromGroup(rawValue)
            listOfValues.add(value)
        }
        return listOfValues
    }

    fun getNextValue(): String {
        val nextContent = contentIterator.next()
        return getValueFromGroup(nextContent.groupValues)
    }

    private fun getValueFromGroup(rawValue: List<String>): String {
        for (index in 1 until 4) {
            if (rawValue[index] !== "") {
                return rawValue[index]
            }
        }
        return "null"
    }
}