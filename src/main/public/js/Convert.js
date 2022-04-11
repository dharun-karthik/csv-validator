class Convert{
    csvToJson(lines) {
        const result = [];
        const headers = lines[0].split(",");
        for (let i = 1; i < lines.length; i++) {
            const obj = {};
            const currentLine = lines[i].split(",");
            for (let j = 0; j < headers.length; j++) {
                if (currentLine[j] == "") {
                    obj[headers[j]] = "null"
                    continue;
                }
                obj[headers[j]] = currentLine[j];
            }
            result.push(obj);
        }
        return result;
    }
}

module.exports = Convert