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
        cy.get('#text_file_id').selectFile('src/main/public/assets/sample3.txt',{
        })
        cy.get('#max-len').type(19)
        cy.get('#min-len').type(7)
        cy.get('#value-submit-button').click()
    })
})

describe('check dropdown option values',()=>
{

     it('check first option value',()=>
       {
            cy.get('select').select(1).should('have.value','Number')
        })

        it('check second option value',()=>
               {
                    cy.get('select').select(2).should('have.value','AlphaNumeric')
                })
})

describe('upload csv-file',()=>{
    it('check if csv file successfully uploaded',()=>{
        cy.get('#csv_id').selectFile('src/main/public/assets/data.csv')
        cy.get('#csv-submit-button').click()
    })
})

