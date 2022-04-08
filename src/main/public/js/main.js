const payload = [];

function getFakeResponse() {
    return [
        {"3": "Row Duplicated From 1"},
        {"4": "Row Duplicated From 1"},
        {"5": "Row Duplicated From 2"}
    ]
}

function csvReader() {
    let csv = document.getElementById("csv_id").files[0];
    const reader = new FileReader();
    reader.onload = async function (event) {
        csv = event.target.result
        const lines = csv.toString().split("\n");
        console.log(lines)
        const result = [];
        const headers = lines[0].split(",");
        for (let i = 1; i < lines.length; i++) {
            const obj = {};
            const currentLine = lines[i].split(",");
            for (let j = 0; j < headers.length; j++) {
                obj[headers[j]] = currentLine[j];
            }
            result.push(obj);
        }


        const response = await fetch('csv', {
            method: 'POST',
            body: JSON.stringify(result)
        })
        if (response.status === 200) {
            const jsonData = await response.json();
            console.log(jsonData)
        }
        response.forEach(element => {
            const node = document.createElement("li");
            const textNode = document.createTextNode(`Line Number ${Object.keys(element)[0]}: ${element[Object.keys(element)[0]]}`);
            node.appendChild(textNode);
            document.getElementById("error_msgs_list").appendChild(node)
        });

    };
    reader.readAsText(csv);
}

function addDataToJson() {
    let jsonObj = {}
    const field = document.getElementById("field");
    var type = document.getElementById("type")
    var value = document.getElementById("text_file_id").files[0]
    var max_len = document.getElementById("max-len")
    var min_len = document.getElementById("min-len")
    var fixed_len = document.getElementById("fixed-len")
    jsonObj["fieldName"] = field.value
    jsonObj["type"] = type.value
    let reader = new FileReader();
    reader.addEventListener('load', function (e) {
        let text = e.target.result
        jsonObj["values"] = text.split('\n')
    });
    reader.readAsText(value)
    jsonObj["maxLength"] = max_len.value
    jsonObj["minLength"] = min_len.value
    jsonObj["length"] = fixed_len.value
    payload.push(jsonObj)
}

async function sendConfigData() {

    const resp = await fetch('add-meta-data', {
        method: 'POST',
        body: JSON.stringify(payload)
    });
    if (resp.status === 200) {
        const jsonData = await resp.json();
        console.log(jsonData)
    }

}
