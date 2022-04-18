async function uploadCSV() {
    let csvElement = document.getElementById('csv-id').files[0];
    const reader = new FileReader();
    reader.onload = await handleCsvFile
    reader.readAsText(csvElement)
    alert("CSV file submitted")
}

async function handleCsvFile(event) {
    const csv = event.target.result;
    const lines = csv.toString().split("\n");
    const result = csvToJson(lines);
    const response = await sendRequest(result);
    await handleResponse(response);
}

async function sendRequest(result) {
    return await fetch('csv', {
        method: 'POST', body: JSON.stringify(result)
    });
}

async function handleResponse(response) {
    console.log(response)
    if (response.status === 200) {
        const jsonData = await response.json();
        sessionStorage.clear();
        sessionStorage.setItem('errors', JSON.stringify(jsonData))
    } else {
        console.log("Error : ", response)
    }
    window.location.href = "errors.html"
}