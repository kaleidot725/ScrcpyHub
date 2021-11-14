package model.utils

import java.io.File

object FileUtils {
    fun createDirFile(dirName: String): File {
        return File(createDirPath(dirName))
    }

    fun createFileFile(dirName: String, fileName: String): File {
        return File(createFilePath(dirName, fileName))
    }

    private fun createDirPath(dirName: String): String {
        val home = System.getProperty("user.home")
        return "$home$dirName"
    }

    private fun createFilePath(dirName: String, fileName: String): String {
        val dirPath = createDirPath(dirName)
        return "$dirPath$fileName"
    }
}
