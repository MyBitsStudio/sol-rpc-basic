package io.utils.logger


object Logger {
    var INFO: Int = 0
    var WARNING: Int = 1
    var ERROR: Int = 2
    var SUCCESS: Int = 3

    fun logToSystem(message: String, type: Int) {
        when (type) {
            0 -> println(LoggerConstants.ANSI_BLUE + "[INFO] " + message + LoggerConstants.ANSI_RESET)
            1 -> println(LoggerConstants.ANSI_YELLOW + "[WARNING] " + message + LoggerConstants.ANSI_RESET)
            2 -> println(LoggerConstants.ANSI_RED + "[ERROR] " + message + LoggerConstants.ANSI_RESET)
            3 -> println(LoggerConstants.ANSI_GREEN + "[SUCCESS] " + message + LoggerConstants.ANSI_RESET)
        }
    }
}
