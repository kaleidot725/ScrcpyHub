package model.command.factory

interface CommandFactory<T> {
    val path: String?
    fun create(data: T): List<String>
    fun createHelp(): List<String>
}
