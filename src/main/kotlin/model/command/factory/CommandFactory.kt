package model.command.factory

interface CommandFactory<T> {
    val path: String?
    val envPath: String

    fun create(data: T): List<String>
    fun createHelp(): List<String>
}