const headers = [];
let totalChunks = 1;

async function sendFileInParts(chunkSize, numberOfChunks, fileSize, file) {
    const response = await divideChunksAndUpload(chunkSize, numberOfChunks, fileSize, file);
    console.log(`response is : ${response}`)
    if (response.status === 200) {
        window.location.href = 'addRules.html'
        return
    }
    window.location.href = "pages/404.html"
}

async function uploadCSV() {
    showLoadingDialog()
    let csvFileElement = document.getElementById('csv-id');
    let file = csvFileElement.files[0];
    captureFirstLine(file)
    const chunkSize = 5000000
    const fileSize = file.size
    let numberOfChunks = Math.ceil(file.size / chunkSize)
    totalChunks = numberOfChunks
    await sendResetConfigRequest()
    await sendFileInParts(chunkSize, numberOfChunks, fileSize, file);
}

async function divideChunksAndUpload(chunkSize, numberOfChunks, fileSize, file) {
    let start = 0
    let end = chunkSize
    let response
    while (numberOfChunks > 0) {
        end = Math.min(end, fileSize)
        let fileChunk = file.slice(start, end)
        sendUploadingStatus(numberOfChunks)
        response = await uploadChunk(fileChunk, start, end, fileSize)
        start = end
        end += chunkSize
        numberOfChunks--
    }
    return response
}

async function uploadChunk(fileChunk, start, end, size) {
    return await fetch("upload-csv", {
        method: "POST",
        headers: {
            "content-range": `bytes ${start}-${end}/${size}`
        },
        body: fileChunk
    })
}

function sendUploadingStatus(currentChunkNumber) {
    let percentageUploaded = Math.ceil((totalChunks - currentChunkNumber) / totalChunks * 100);
    console.log(`File uploading: ${percentageUploaded}`)
    document.getElementById('upload-percentage').innerText = String(percentageUploaded)
}

function showLoadingDialog() {
    const blurBg = document.getElementById('csv-page')
    blurBg.classList.toggle('active')
    document.getElementById('uploading-pop-up').style.display = 'flex'
}

function captureFirstLine(file) {
    const reader = new FileReader();
    reader.onload = handleCsvFile
    reader.readAsText(file)
}

async function handleCsvFile(event) {
    const csv = event.target.result;
    const lines = csv.toString().split("\n");
    captureHeaders(lines[0])
}

function captureHeaders(headersString) {
    let headersList = headersString.split(',')
    headersList.forEach(element => {
        let newElement = element.replaceAll("\"", "");
        headers.push(newElement)
    });
    sessionStorage.removeItem('headers')
    sessionStorage.setItem('headers', JSON.stringify(headers))
}

async function sendResetConfigRequest() {
    await fetch('reset-config', {
        method: 'DELETE',
    });
}