package validation.jsonConfig

import metaData.template.JsonConfigTemplate

class MaxMinValidator: ConfigValidator {
    override fun validate(jsonField: JsonConfigTemplate): List<String> {
        val error = mutableListOf<String>()
        if(jsonField.minLength !=null && jsonField.maxLength!=null && jsonField.minLength > jsonField.maxLength){
            error.add("Max length : ${jsonField.maxLength} should be greater than min length : ${jsonField.minLength}")
        }
        return error
    }
}