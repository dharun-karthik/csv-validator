describe('cypress connect test',()=>{
    it('Open link',()=>{
        cy.visit("http://localhost:3000")
    })
})

describe('Send the config file',()=>
{
    it('fill the values and submit the config file',()=>
    {
        cy.get('#field').type('Hello')
        cy.get('select').select(1)
        cy.get('#text_file_id').selectFile('/Users/gauravm/Desktop/csv_parse/src/main/public/data.csv',{
        })
        cy.get('#max-len').type(19)
        cy.get('#min-len').type(7)
        cy.get('#value-submit-button').click()
    })
})

describe('check dropdown option values',()=>
{
    it('check the value of drop down button',()=>
   {
        cy.get('select').select(1).should('have.value','Number')
    })
})
describe('upload csv-file',()=>{
    it('check if csv file sucessfully uploaded',()=>{
        cy.get('#csv_id').selectFile('/Users/gauravm/Desktop/csv_parse/src/main/public/data.csv')
        cy.get('#csv-submit-button').click()
    })
})

