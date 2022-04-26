function csvToJson(text, headers) {
    let quoteChar = '"'
    let delimiter = ','
    const regex = new RegExp(`\\s*(${quoteChar})?(.*?)\\1\\s*(?:${delimiter}|$)`, 'gs');
  
    const match = line => {
      const matches = [...line.matchAll(regex)].map(m => m[2]);
      matches.pop();
      return matches;
    }
  
    const lines = text.split('\n');
    const heads = headers ?? match(lines.shift());
  
    return lines.map(line => {
      return match(line).reduce((acc, cur, i) => {
        const val = (cur == "" || cur == "\r") ? "null" : cur;
        const key = heads[i].toLowerCase() ?? `extra_${i}`;
        return { ...acc, [key]: val };
      }, {});
    });
  }

function arrangeDependencies(payload) {
    for (let field in payload) {
        for (let key in payload[field]) {
            if (key == "dependentOn") {
                assignDependencies(payload, field);
            }
        }
    }
    return payload;
}

function assignDependencies(payload, field) {
    payload[field]["dependencies"] = [{
        "dependentOn": payload[field]["dependentOn"],
        "expectedDependentFieldValue": payload[field]["expectedDependentFieldValue"],
        "expectedCurrentFieldValue": payload[field]["expectedCurrentFieldValue"]
    }];
}

function convertPayloadToJsonArray(payload) {
    console.log(`payload ${payload}`)
    let jsonArray = []
    let index = 0;
    payload = arrangeDependencies(payload);
    for (let field in payload) {
        jsonArray[index++] = payload[field];
    }
    console.log(jsonArray);
    return jsonArray;
}


function lowerCase(data, field = "") {
    if (field == 'pattern') {
        return data
    }
    return String(data).toLowerCase()
}