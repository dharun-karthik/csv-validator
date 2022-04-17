const rewire = require("rewire")

const convert = rewire("../src/main/public/js/Convert.js");

describe("convert", () => {
    it("should convert csv to json", () => {
        let lines = ["a,b,c", "1,xyz,1.2", "2,pqr,2.4"]
        let csvToJson = convert.__get__("csvToJson")
        let expected = [{a: '1', b: 'xyz', c: '1.2'}, {a: '2', b: 'pqr', c: '2.4'}]
        let actual = csvToJson(lines);

        expect(actual).toEqual(expected)
    })
})