const MandatoryInputValidation = require('../src/main/public/js/MandatoryInputValidation.js')
describe("mandatory input validation", () => {
    it('should return false if mandatory input for field is empty', ()=>{
        let mandatoryInputValidation = new MandatoryInputValidation();
        let isValid = mandatoryInputValidation.validateInputsForFields("Price", "");
        expect(isValid).toBeFalse();
    })
})