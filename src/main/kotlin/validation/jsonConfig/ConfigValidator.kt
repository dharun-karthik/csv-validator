package validation.jsonConfig

import metaData.template.JsonConfigTemplate

interface ConfigValidator {
    fun validate(jsonField: JsonConfigTemplate): List<String>
}