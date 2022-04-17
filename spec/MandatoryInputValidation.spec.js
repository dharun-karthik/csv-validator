
const rewire = require("rewire")
const mandatoryInputValidation = rewire("../src/main/public/js/MandatoryInputValidation");

describe("mandatory input validation for field", () => {
    it('should return false if mandatory input for field is empty', () => {
        let validateInputsForFields = mandatoryInputValidation.__get__("validateInputsForFields");
        let isValid = validateInputsForFields("Price", "");
        expect(isValid).toBeFalse();
    })

    it('should return true if mandatory input for field is not empty', () => {
        let validateInputsForFields = mandatoryInputValidation.__get__("validateInputsForFields");
        let isValid = validateInputsForFields("Price", "Number");
        expect(isValid).toBeTrue();
    })
})

describe("mandatory input validation for dependency", () => {
    it('should return false if both mandatory input for dependency is empty', () => {
        let validateInputsForDependency = mandatoryInputValidation.__get__("validateInputsForDependency");
        let isValid = validateInputsForDependency("Export", "", "");
        expect(isValid).toBeFalse();
    })

    it('should return false if any mandatory input for dependency is empty', () => {
        let validateInputsForDependency = mandatoryInputValidation.__get__("validateInputsForDependency");
        let isValid = validateInputsForDependency("Export", "Y", "");
        expect(isValid).toBeFalse();
    })

    it('should return false if mandatory input for dependency is not empty', () => {
        let validateInputsForDependency = mandatoryInputValidation.__get__("validateInputsForDependency");
        let isValid = validateInputsForDependency("Export", "Y", "Present");
        expect(isValid).toBeTrue();
    })
})

