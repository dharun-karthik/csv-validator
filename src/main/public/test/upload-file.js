const upload = async () => {
    const fileElement = document.getElementById("myFile")
    const file = fileElement.files[0]
    const chunkSize = 40000
    const fileSize = file.size
    let numberOfChunks = Math.ceil(file.size / chunkSize)
    let start = 0
    let end = chunkSize
    while (numberOfChunks > 0) {
        end = Math.min(end, fileSize)
        let fileChunk = file.slice(start, end)
        await uploadChunk(fileChunk)
        start = end
        end += chunkSize
        numberOfChunks--
        console.log(numberOfChunks, start, end, fileSize)
        console.log(fileChunk)
    }
}

const uploadChunk = async (fileChunk) => {
    await fetch("file-in-chunks", {
        method: "POST",
        headers: {
            "content-length": fileChunk.size
        },
        body: fileChunk
    })
}