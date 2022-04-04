var payload=[]
function csvReader() {
    var csv = document.getElementById("csv_id").files[0];
    const reader = new FileReader();
    reader.onload = function (event) {
        csv = event.target.result
        var lines = csv.toString().split("\n");
        console.log(lines)
        var result = [];
        var headers = lines[0].split(",");
        for (var i = 1; i < lines.length; i++) {
            var obj = {};
            var currentline = lines[i].split(",");
            for (var j = 0; j < headers.length; j++) {
                obj[headers[j]] = currentline[j];
            }
            result.push(obj);
        }
        console.log(JSON.stringify(result))

        fetch('csv', {

            method: 'POST',
            body: JSON.stringify(result)
        })
    };
    reader.readAsText(csv);
}

function addDataToJson() {
        let jsonObj = {}
        var field = document.getElementById("field")
        var type = document.getElementById("type")
        var value = document.getElementById("text_file_id").files[0]
        jsonObj["field-name"] = field.value
        jsonObj["type"] = type.value
        let reader = new FileReader();
        reader.addEventListener('load', function(e) {
          let text = e.target.result
          jsonObj["values"] = text.split('\n')

        });
        reader.readAsText(value)
        payload.push(jsonObj)
}

function sendConfigData(){
fetch('add-meta-data', {
            method: 'POST',
            body: JSON.stringify(payload)
        })
}