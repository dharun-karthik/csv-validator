class Convert {
    csvToJson(lines) {
        const result = [];
        const headerWithoutTrim = lines[0].split(",");
        const headers = headerWithoutTrim.map(element => element.trim().toLowerCase())
        for (let i = 1; i < lines.length; i++) {
            const obj = {};
            const currentLine = lines[i].split(",");
            for (let j = 0; j < headers.length; j++) {
                if (currentLine[j] == "") {
                    obj[headers[j]] = "null"
                    continue;
                }
                if (currentLine[j] == undefined) {
                    obj[headers[j]] = currentLine[j]
                } else {
                    obj[headers[j]] = currentLine[j].trim();
                }
            }
            result.push(obj);
        }
        return result;
    }
}