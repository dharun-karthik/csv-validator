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
        cy.get('#type').type('alphanumeric')
        cy.get('#text_file_id').selectFile('/Users/soumya/csv_parse/src/main/public/data.csv',{
        })
        cy.get('#value-submit-button').click()
    })
})

describe('upload csv-file',()=>{
    it('check if csv file sucessfully uploaded',()=>{
        cy.get('#csv_id').selectFile('/Users/soumya/csv_parse/src/main/public/data.csv')
        cy.get('#csv-submit-button').click()
    })
})

describe('upload csv-file',()=>{
    it('check if csv file sucessfully uploaded',()=>{
        cy.readFile('/Users/soumya/csv_parse/src/main/public/data.csv').its('ProductID').should('12')

    })
})