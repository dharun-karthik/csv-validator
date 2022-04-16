package validation.implementation

class DependencyValidation {
    fun validate(
        currentValue: String,
        dependentValue: String,
        expectedDependentFieldValue: String,
        expectedCurrentFieldValue: String
    ): Boolean {
        if (isNull(expectedDependentFieldValue)) {
            if (checkNull(dependentValue)) {
                return checkValue(expectedCurrentFieldValue, currentValue)
            }
            return true
        } else if (isNotNull(expectedDependentFieldValue)) {
            if (checkNotNull(dependentValue)) {
                return checkValue(expectedCurrentFieldValue, currentValue)
            }
        }
        if (checkEquals(expectedDependentFieldValue, dependentValue)) {
            return checkValue(expectedCurrentFieldValue, currentValue)
        }
        return true
    }

    private fun checkValue(expectedCurrentFieldValue: String, currentValue: String): Boolean {
        if (isNull(expectedCurrentFieldValue) && checkNull(currentValue)) {
            return true
        }
        if (isNotNull(expectedCurrentFieldValue) && checkNotNull(currentValue)) {
            return true
        }
        if (checkEquals(expectedCurrentFieldValue, currentValue)) {
            return true
        }
        return false
    }

    private fun checkEquals(expectedCurrentFieldValue: String, currentValue: String): Boolean {
        return expectedCurrentFieldValue.contentEquals(currentValue, ignoreCase = true)
    }

    fun isNull(value: String): Boolean {
        return value.contentEquals("null", ignoreCase = true)
    }

    fun isNotNull(value: String): Boolean {
        return value.contentEquals("!null", ignoreCase = true)
    }

    private fun checkNull(value: String): Boolean {
        return isNull(value)
    }

    private fun checkNotNull(value: String): Boolean {
        return !isNull(value)
    }
}