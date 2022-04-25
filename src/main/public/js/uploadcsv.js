const headers = [];

async function uploadCSV() {
    let csvElement = document.getElementById('csv-id').files[0];
    await sendResetConfigRequest()
    const reader = new FileReader();
    reader.onload = await handleCsvFile
    reader.readAsText(csvElement)
}

async function sendResetConfigRequest() {
    await fetch('reset-config', {
        method: 'DELETE',
    });
}

async function handleCsvFile(event) {
    const csv = event.target.result;
    const lines = csv.toString().split("\n");
    const result = csvToJson(lines);
    console.log(result)
    const response = await sendRequest(result);
    await handleResponse(response);
}

function captureHeaders(headersList) {
    headersList.forEach(element => {
        headers.push(element)
    });
    sessionStorage.removeItem('headers')
    sessionStorage.setItem('headers', JSON.stringify(headers))
}


async function sendRequest(result) {
    return await fetch('csv', {
        method: 'POST', body: JSON.stringify(result)
    });
}

async function handleResponse(response) {
    console.log(response)
    if (response.status === 200) {
        await response.json();
        window.location.href = 'addRules.html'
        return
    }
    window.location.href = "pages/404.html"
}