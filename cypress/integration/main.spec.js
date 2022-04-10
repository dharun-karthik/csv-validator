describe('cypress connect test', () => {
    it('Open link', () => {
        cy.visit("http://localhost:3000")
    })
})

describe('Add config data', () => {
    it('fill the values and add the config data', () => {
        cy.get('#field').type('Pincode')
        cy.get('#text_file_id').selectFile('src/main/public/assets/valueTestSample.txt', {})
        cy.get('#type').select(1)
        cy.get('#max-len').type(19)
        cy.get('#min-len').type(7)

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#add-field-button').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Field: Pincode is added to configuration of CSV')
        })
        cy.get('#config span:first').contains('Pincode')
        cy.get('#config span').contains('Number')
        cy.get('#config span').contains('19')
        cy.get('#config span').contains('7')
    })
})

describe('Dependency test with one dependency', () => {
    it('and all values filled', () => {
        cy.reload()
        cy.get('#field').type('Country Name')
        cy.get('#text_file_id').selectFile('src/main/public/assets/valueTestSample.txt', {})
        cy.get('#type').select(2)
        cy.get('#max-len').type(5)
        cy.get('#min-len').type(2)

        cy.get('#dependentOnColumn').type('Export')
        cy.get('#expectedDependentFieldValue').type('N')
        cy.get('#expectedCurrentFieldValue').select(2)
        cy.get('#add-dependency-button').click()

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#add-field-button').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Field: Country Name is added to configuration of CSV')
        })

        cy.get('#config span:first').contains('Country Name')
        cy.get('#dependency span:first').contains('Export')
    })

    it('and missing dependent field and current field values', () => {
        cy.reload()
        cy.get('#field').type('Country Name')
        cy.get('#text_file_id').selectFile('src/main/public/assets/valueTestSample.txt', {})
        cy.get('#type').select(2)
        cy.get('#max-len').type(5)
        cy.get('#min-len').type(2)

        cy.get('#dependentOnColumn').type('Export')

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#add-dependency-button').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Please enter values for Expected Dependent Field and Expected Current Field')
        })
    })

    it('and missing dependent field value', () => {
        cy.reload()
        cy.get('#field').type('Country Name')
        cy.get('#text_file_id').selectFile('src/main/public/assets/valueTestSample.txt', {})
        cy.get('#type').select(2)
        cy.get('#max-len').type(5)
        cy.get('#min-len').type(2)

        cy.get('#dependentOnColumn').type('Export')
        cy.get('#expectedCurrentFieldValue').select(2)

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#add-dependency-button').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Please enter values for Expected Dependent Field and Expected Current Field')
        })
    })

    it('and missing current field value', () => {
        cy.reload()
        cy.get('#field').type('Country Name')
        cy.get('#text_file_id').selectFile('src/main/public/assets/valueTestSample.txt', {})
        cy.get('#type').select(2)
        cy.get('#max-len').type(5)
        cy.get('#min-len').type(2)

        cy.get('#dependentOnColumn').type('Export')
        cy.get('#expectedCurrentFieldValue').select(2)

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#add-dependency-button').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Please enter values for Expected Dependent Field and Expected Current Field')
        })
    })
})

