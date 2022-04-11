const Convert = require('../src/main/public/js/Convert.js')

describe("convert", () => {
    it("should convert csv to json", () => {
        let lines = ["a,b,c","1,xyz,1.2","2,pqr,2.4"]
        let convert = new Convert();
        let expected =  [{a: '1', b: 'xyz', c: '1.2'},
                        {a: '2', b: 'pqr', c: '2.4'}]
        let actual = convert.csvToJson(lines);
    
        expect(actual).toEqual(expected)
    })
})