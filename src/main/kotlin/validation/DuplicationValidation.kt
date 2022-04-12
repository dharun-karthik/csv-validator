package validation

class DuplicationValidation {
    private val mapOfObjectsAndIndices: MutableMap<String, Int> = mutableMapOf()

    fun isDuplicateIndexAvailable(
        element: Any,
        index: Int,
    ): Int? {
        val elementInString = element.toString()
        val previousDuplicateIndexValue = mapOfObjectsAndIndices[elementInString]
        if (previousDuplicateIndexValue == null) {
            mapOfObjectsAndIndices[elementInString] = index + 1
            return null
        }
        return previousDuplicateIndexValue
    }
}