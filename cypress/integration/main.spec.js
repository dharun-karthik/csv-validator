describe('cypress connect test', () => {
    it('Open link', () => {
        cy.visit("http://localhost:3000")
    })
})

/*
describe('Upload empty configuration', () => {
    it('should give alert', () => {
        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#upload-configs').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Enter Mandatory Fields!')
        })
    })
})

describe('Not upload all mandatory fields', () => {
    it('should give alert to enter all mandatory fields', ()  => {
        cy.get("#field0").type("Operations")
        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#upload-configs').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Enter Mandatory Fields!')
        })
    })
})

describe('Add config data', () => {
    it('should fill the values', () => {
        cy.reload()
        cy.get('#field0').type('Operations')
        cy.get('#type0').select(2)
        cy.get('#max-len0').type(19)
        cy.get('#min-len0').type(7)

    })

    it('should add another field and fill the values', () => {
        cy.get("#add-new-field").click()
        cy.get("#field1").type('completedAt')
        cy.get("#type1").select(6)
        cy.get("#date-time-format1").select(9)

    })

    it('should be able to submit the configuration', () => {
        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#upload-configs').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Submitted configuration of CSV\nNow you can add your csv file.')
        })
    })
})

describe("Upload CSV", () => {
    it("should upload csv for validation", () => {
        cy.get('#csv-id').selectFile('cypress/fixtures/incorrectColumnData.csv')

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#csv-submit-button').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('CSV file submitted')
        })
    })
})

describe("After selecting CSV, Display", () => {

    beforeEach(()=>{
        cy.visit("http://localhost:3000")
        cy.get("#field0").type("Product Id")
        cy.get("#type0").select(1)
        cy.get("#fixed-len0").type("5")

        cy.get("#add-new-field").click()

        cy.get("#field1").type("Product Description")
        cy.get("#type1").select(3)

        cy.get("#add-new-field").click()

        cy.get("#field2").type("Price")
        cy.get("#type2").select(1)

        cy.get("#add-new-field").click()

        cy.get("#field3").type("Export")
        cy.get("#type3").select(3)
        cy.get("#fixed-len3").type("1")

        cy.get("#add-new-field").click()

        cy.get("#field4").type("Country Name")
        cy.get("#type4").select(3)

        cy.get("#add-new-field").click()

        cy.get("#field5").type("Source City")
        cy.get("#type5").select(3)

        cy.get("#add-new-field").click()

        cy.get("#field6").type("Country Code")
        cy.get("#type6").select(1)

        cy.get("#add-new-field").click()

        cy.get("#field7").type("Source Pincode")
        cy.get("#type7").select(1)


    })
    it("no errors for valid csv", () => {
        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#upload-configs').click().then(() => {
            expect(stub.getCall(0)).to.be.calledWith('Submitted configuration of CSV\nNow you can add your csv file.')
        })
        cy.get('#csv-id').selectFile('cypress/fixtures/correctData.csv')
        cy.get('#csv-submit-button').click()


        cy.get('#valid-csv-id').should(($h3) => {
            expect($h3).to.contain('CSV Has No Errors')
        })

    })

    // it("errors for duplicate data in csv", () => {
    //     cy.get('#csv-id').selectFile('cypress/fixtures/duplicateData.csv')
    //     cy.get('#csv-submit-button').click()

    //     cy.get('#error_msg_list li').should(($li) => {
    //         expect($li).to.contain('Row Duplication')
    //     })
    // })

    // it("errors for incorrect column in csv", () => {
    //     cy.get('#csv-id').selectFile('cypress/fixtures/incorrectColumnData.csv')
    //     cy.get('#csv-submit-button').click()

    //     cy.get('#error_msg_list li').should(($li) => {
    //         expect($li).to.contain('Column unavailable in config')
    //     })
    // })

    // it("errors for incorrect dependency in csv", () => {
    //     cy.get('#csv-id').selectFile('cypress/fixtures/incorrectDependencyData.csv')
    //     cy.get('#csv-submit-button').click()

    //     cy.get('#error_msg_list li').should(($li) => {
    //         expect($li).to.contain('Dependency Error')
    //     })
    // })
})
*/




