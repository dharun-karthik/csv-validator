const upload = async () => {
    const fileElement = document.getElementById("myFile")
    const file = fileElement.files[0]
    const chunkSize = 5000000
    const fileSize = file.size
    let numberOfChunks = Math.ceil(file.size / chunkSize)
    let start = 0
    let end = chunkSize
    while (numberOfChunks > 0) {
        end = Math.min(end, fileSize)
        let fileChunk = file.slice(start, end)
        console.log(numberOfChunks, start, end, fileSize)
        await uploadChunk(fileChunk, start, end, fileSize)
        start = end
        end += chunkSize
        numberOfChunks--
    }
}

const uploadChunk = async (fileChunk, start, end, size) => {
    console.log("length ", fileChunk.length)
    await fetch("file-in-chunks", {
        method: "POST",
        headers: {
            "content-range": `bytes ${start}-${end}/${size}`
        },
        body: fileChunk
    })
}