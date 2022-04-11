const MandatoryInputValidation = require('../src/main/public/js/MandatoryInputValidation.js')

describe("mandatory input validation for field", () => {
    it('should return false if mandatory input for field is empty', ()=>{
        let mandatoryInputValidation = new MandatoryInputValidation();
        let isValid = mandatoryInputValidation.validateInputsForFields("Price", "");
        expect(isValid).toBeFalse();
    })

    it('should return true if mandatory input for field is not empty', ()=>{
        let mandatoryInputValidation = new MandatoryInputValidation();
        let isValid = mandatoryInputValidation.validateInputsForFields("Price", "Number");
        expect(isValid).toBeTrue();
    })
})

describe("mandatory input validation for dependency", () => {
    it('should return false if both mandatory input for dependency is empty', ()=>{
        let mandatoryInputValidation = new MandatoryInputValidation();
        let isValid = mandatoryInputValidation.validateInputsForDependency("Export", "", "");
        expect(isValid).toBeFalse();
    })

    it('should return false if any mandatory input for dependency is empty', ()=>{
        let mandatoryInputValidation = new MandatoryInputValidation();
        let isValid = mandatoryInputValidation.validateInputsForDependency("Export", "Y", "");
        expect(isValid).toBeFalse();
    })

    it('should return false if mandatory input for dependency is not empty', ()=>{
        let mandatoryInputValidation = new MandatoryInputValidation();
        let isValid = mandatoryInputValidation.validateInputsForDependency("Export", "Y", "Present");
        expect(isValid).toBeTrue();
    })
})

