const headers = [];

async function uploadCSV() {
    showLoadingDialog()
    let csvElement = document.getElementById('csv-id').files[0];
    await sendResetConfigRequest()
    const reader = new FileReader();
    reader.onload = await handleCsvFile
    reader.readAsText(csvElement)
}

function showLoadingDialog() {
    const blurBg = document.getElementById('csv-page')
    blurBg.classList.toggle('active')
    document.getElementById('uploading-pop-up').style.display = 'flex'
}

async function sendResetConfigRequest() {
    await fetch('reset-config', {
        method: 'DELETE',
    });
}

async function handleCsvFile(event) {
    const csv = event.target.result;
    const lines = csv.toString().split("\n");
    captureHeaders(lines[0])
    const result = csvToJson(csv);
    console.log(result)
    const response = await sendRequest(result);
    await handleResponse(response);
}

function captureHeaders(headersString) {
    headersList = headersString.split(',')
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
        const jsonData = await response.json();
        window.location.href = 'addRules.html'
        return
    }
    window.location.href = "pages/404.html"
}