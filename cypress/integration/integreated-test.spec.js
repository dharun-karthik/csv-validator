/*
describe("testing the entire application", () => {
    it('Open link', () => {
        cy.visit("http://localhost:3000")
    })

    let jsonArray = JSON.parse("[\n" +
        "  {\n" +
        "    \"fieldName\": \"Product id\",\n" +
        "    \"type\": \"AlphaNumeric\",\n" +
        "    \"length\": \"5\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"fieldName\": \"Product description\",\n" +
        "    \"type\": \"AlphaNumeric\",\n" +
        "    \"minLength\": \"7\",\n" +
        "    \"maxLength\": \"20\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"fieldName\": \"price\",\n" +
        "    \"type\": \"Number\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"fieldName\": \"Export\",\n" +
        "    \"type\": \"Alphabets\",\n" +
        "    \"values\": \"cypress/fixtures/export.txt\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"fieldName\": \"country name\",\n" +
        "    \"type\": \"Alphabets\",\n" +
        "    \"minLength\": \"3\",\n" +
        "    \"dependencies\": [\n" +
        "      {\n" +
        "        \"dependentOn\": \"export\",\n" +
        "        \"expectedDependentFieldValue\": \"n\",\n" +
        "        \"expectedCurrentFieldValue\": \"null\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"dependentOn\": \"export\",\n" +
        "        \"expectedDependentFieldValue\": \"y\",\n" +
        "        \"expectedCurrentFieldValue\": \"!null\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"fieldName\": \"source city\",\n" +
        "    \"type\": \"Alphabets\",\n" +
        "    \"minLength\": \"3\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"fieldName\": \"country code\",\n" +
        "    \"type\": \"Number\",\n" +
        "    \"maxLength\": \"3\",\n" +
        "    \"dependencies\": [\n" +
        "      {\n" +
        "        \"dependentOn\": \"country name\",\n" +
        "        \"expectedDependentFieldValue\": \"!null\",\n" +
        "        \"expectedCurrentFieldValue\": \"!null\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"dependentOn\": \"country name\",\n" +
        "        \"expectedDependentFieldValue\": \"null\",\n" +
        "        \"expectedCurrentFieldValue\": \"null\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"fieldName\": \"source pincode\",\n" +
        "    \"type\": \"Number\",\n" +
        "    \"length\": \"6\",\n" +
        "    \"values\": \"cypress/fixtures/valueTestSample.txt\"\n" +
        "  }\n" +
        "]")

    it("should be able to clear previous fields", () => {
        cy.get("#reset_config").click()
        cy.on('window:confirm', () => true);
    })

    it("should be able to add fields", () => {

        for (let json in jsonArray) {
            let current = jsonArray[json]
            cy.get("#field").type(current.fieldName)
            cy.get('#type').select(current.type)
            if (current.minLength !== undefined) {
                cy.get('#min-len').type(current.minLength)
            }
            if (current.maxLength !== undefined) {
                cy.get('#max-len').type(current.maxLength)
            }
            if (current.length !== undefined) {
                cy.get('#fixed-len').type(current.length)
            }
            if (current.values !== undefined) {
                cy.get('#text_file_id').selectFile(current.values, {})
            }

            const stub = cy.stub()
            cy.on('window:alert', stub)
            cy.get('#add-field-button').click()

            if (current.dependencies !== undefined) {
                for (let dependency in current.dependencies) {
                    let currentDep = current.dependencies[dependency]
                    cy.get('#dependentOnColumn').type(currentDep.dependentOn)
                    cy.get('#expectedDependentFieldValue').type(currentDep.expectedDependentFieldValue)
                    cy.get('#expectedCurrentFieldValue').select(currentDep.expectedCurrentFieldValue)
                    cy.get('#add-dependency-button').click()
                }
            }
            cy.get('#value-submit-button').click()
        }
    })

    it("should be able to upload the file and validate", () => {
        cy.get("#csv_id").selectFile("cypress/fixtures/correctData.csv", {})
        cy.get("#csv-submit-button").click()
        cy.get("div[id='error-msg'] h3").should(error => {
            expect(error).to.contain("CSV IS VALID")
        })
    })
})

 */