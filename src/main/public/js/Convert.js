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
    delete payload[field]["dependentOn"]
    delete payload[field]["expectedDependentFieldValue"]
    delete payload[field]["expectedCurrentFieldValue"]
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