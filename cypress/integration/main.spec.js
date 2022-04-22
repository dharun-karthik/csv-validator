describe('cypress connect test', () => {
    it('Open link', () => {
        cy.visit("http://localhost:3000")
    })
})

describe('content of landing page', () => {
    it('should contain heading as One place to Validate all your CSVs', () => {
        cy.get('h1:first').should('have.text', 'One place to Validate all your CSVs')
    })

    it('should contain heading as Step 1: Upload CSV', () => {
        cy.get('.configs-heading').should('have.text', 'Step 1: Upload CSV')
    })

    it('should contain heading as In just 3 Easy Steps', () => {
        cy.get('h2').should('have.text', 'In just 3 Easy Steps')
    })

    it('should contain heading as Choose or Drag CSV Here', () => {
        cy.get('.heading-to-upload-csv').should('have.text', 'Choose or Drag CSV Here')
    })
})

describe('Uploading file', () => {
     it('should be able to choose csv file for uploading', () => {
        cy.get('#csv-id').selectFile('cypress/fixtures/correctData.csv')
        cy.get('input[type="file"]').attachFile('correctData.csv');
     })

     it('should be able to drag and drop csv file for uploading', () => {
        cy.get('#csv-id').selectFile('cypress/fixtures/correctData.csv')
        cy.get('input[type="file"]').attachFile('correctData.csv', { subjectType: 'drag-n-drop' });
     })

     it('should not be able to redirect to addRules page if no file is selcted after clicking upload', () => {
        cy.on('uncaught:exception', () => {
            return false;
          });
        cy.reload()
        cy.get('#csv-submit-button').click()
        cy.url().should('be.equal', 'http://localhost:3000/')
     })
 })

describe('Setting validation rules', () => {

    it('should be able to alert if mandatory fields are not filled', () => {
        cy.visit('http://localhost:3000/addRules.html')
        cy.get('#upload-configs').click()
        cy.get('#alert-message').then(($message) => {
             expect($message).to.contain('Enter mandatory fields')
         })
        cy.get("#close").click()
    })

    it('should allow user to select and enter values in input box', () => {
        cy.get('#type0').select(1)
        cy.get("#fixed-len0").type("5")
        cy.get('#type1').select(8)
        cy.get('#type2').select(1)
        cy.get('#type3').select(3)
        cy.get('#fixed-len3').type("1")
        cy.get('#type4').select(3)
        cy.get('#type5').select(3)
        cy.get('#type6').select(1)   
        cy.get('#type7').select(1)
    })

    it('should allow  user to add restricted values', () => {
        cy.get("#edit-button3").click()
        cy.get("#value-textbox3").type("Y,N")
    })

    it('should allow user to not save by clicking cross button', () => {
        cy.get("#close-modal3").click({force: true})
    })

    it('should allow user to add and save the restricted values', () => {
        cy.get("#edit-button3").click()
        cy.get("#value-textbox3").type("Y,N")
        cy.get("#save-btn3").click({force: true})
    })
    it('should be able to fill the headers from the csv file', () => {
        cy.visit('http://localhost:3000')
        cy.get('#csv-id').selectFile('cypress/fixtures/correctData.csv')
        cy.get('#csv-submit-button').click()
        cy.visit('http://localhost:3000/addRules.html')
        cy.get('#field0', { timeout: 8000 }).should('have.value', 'Product Id')
        cy.get('#field1').should('have.value', 'Product Description')
        cy.get('#field2').should('have.value', 'Price')
        cy.get('#field3').should('have.value', 'Export')
        cy.get('#field4').should('have.value', 'Country Name')
        cy.get('#field5').should('have.value', 'Source City')
        cy.get('#field6').should('have.value', 'Country Code')
        cy.get('#field7').should('have.value', 'Source Pincode')
        cy.get('#type0').select(8)
        cy.get('#type1').select(8)
        cy.get('#type2').select(8)
        cy.get('#type3').select(8)
        cy.get('#type4').select(8)
        cy.get('#type5').select(8)
        cy.get('#type6').select(8)
        cy.get('#type7').select(8)
        cy.get('#upload-configs').click()
        cy.get('#ok-btn').click()
    })
})

describe('content of error page', () => {
    it('should contain heading as One place to Validate all your CSVs', () => {


        cy.visit('http://localhost:3000/errors.html')
        cy.get('h1:first').should('have.text', 'One place to Validate all your CSVs')
    })

    it('should contain heading as Step 3: Get Validations For Your CSV', () => {
        cy.get('.configs-heading').should('have.text', 'Step 3: Get Validations For Your CSV')
    })

    it('should contain heading as In just 3 Easy Steps', () => {
        cy.get('h2').should('have.text', 'In just 3 Easy Steps')
    })
})

describe("display error", () => {
    it('should give no error for valid csv file', () => {
        cy.get('#valid-csv-id').then(($validHeading) => {
            expect($validHeading).to.contain('CSV Has No Errors')
        })   
    })
})

