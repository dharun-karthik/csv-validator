describe('cypress connect test', () => {
    it('Open link', () => {
        cy.visit("http://localhost:3000")
    })
})


// describe('Upload empty configuration', () => {
//     it('should give alert', () => {
//         cy.get('#upload-configs').click()
//         cy.get('#alert-message').then(($message) => {
//             expect($message).to.contain('Enter mandatory fields')
//         })
//     })
// })


// describe('Not upload all mandatory fields', () => {
//     it('should give alert to enter all mandatory fields', ()  => {
//         cy.reload()
//         cy.get("#field0").type("Operations")
//         cy.get('#upload-configs').click()
//         cy.get('#alert-message').then(($message) => {
//             expect($message).to.contain('Enter mandatory fields')
//         })
//     })
// })

// describe("After selecting CSV, Display", () => {

//     before(() => {
//         cy.request('DELETE', 'http://localhost:3000/reset-config')
//         cy.visit("http://localhost:3000")
//         cy.get("#field0").type("Product Id")
//         cy.get("#type0").select(1)
//         cy.get("#fixed-len0").type("5")

//         cy.get("#add-new-field").click()

//         cy.get("#field1").type("Product Description")
//         cy.get("#type1").select(3)

//         cy.get("#add-new-field").click()

//         cy.get("#field2").type("Price")
//         cy.get("#type2").select(1)

//         cy.get("#add-new-field").click()

//         cy.get("#field3").type("Export")
//         cy.get("#type3").select(3)
//         cy.get("#fixed-len3").type("1")

//         cy.get("#add-new-field").click()

//         cy.get("#field4").type("Country Name")
//         cy.get("#type4").select(3)

//         cy.get("#add-new-field").click()

//         cy.get("#field5").type("Source City")
//         cy.get("#type5").select(3)

//         cy.get("#add-new-field").click()

//         cy.get("#field6").type("Country Code")
//         cy.get("#type6").select(1)

//         cy.get("#add-new-field").click()

//         cy.get("#field7").type("Source Pincode")
//         cy.get("#type7").select(1)
//     })


//     it("no errors for valid csv", () => {
//         cy.get('#upload-configs').click()
//         cy.get('#ok-btn').click()
//         cy.visit("http://localhost:3000/uploadCSV.html")
//         cy.get('#csv-id').selectFile('cypress/fixtures/correctData.csv')
//         cy.get('#csv-submit-button').click()

//         cy.get('#valid-csv-id').then(($validHeading) => {
//             expect($validHeading).to.contain('CSV Has No Errors')
//         })
//     })
 
//     it("clear configs", () => {
//         cy.request('DELETE', 'http://localhost:3000/reset-config')
//     })
// })




