describe('cypress connect test', () => {
    it('Open link', () => {
        cy.visit("http://localhost:3000")
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

     it('should be able to redirect to addRules page after clicking upload', () => {
        cy.get('#csv-id').selectFile('cypress/fixtures/correctData.csv')
        cy.get('#csv-submit-button').click()
        cy.url().should('be.equal', 'http://localhost:3000/addRules.html')
     })
 })



