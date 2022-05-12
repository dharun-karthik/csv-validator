import db.DatabaseSetUp

fun main() {
    DatabaseSetUp().createTables()
    Server(3000).startServer()
}