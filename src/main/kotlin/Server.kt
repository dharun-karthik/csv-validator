import metaData.MetaDataReaderWriter
import routeHandler.RequestHandler
import java.net.ServerSocket


class Server(
    private val port: Int = 3000,
    metaDataPath: String = "src/main/public/csv-meta-data.json"
) {
    private val metaDataReaderWriter = MetaDataReaderWriter(metaDataPath)
    private val requestHandler = RequestHandler(metaDataReaderWriter)
    private val serverSocket = ServerSocket(port)

    fun startServer() {
        println("Sever Running in port $port")
        while (true) {
            val clientSocket = serverSocket.accept()
            ClientHandler().handleClient(clientSocket, requestHandler)
        }
    }

    fun stopServer() {
        serverSocket.close()
    }

}
