package io.sol.tool

import io.sol.connection.Connection
import io.utils.Misc.delay
import io.utils.logger.Logger.logToSystem

class TransactionChecker(private val connection: Connection, private val hash: String, private var checks: Int) {
    private val initialChecks = checks
    private var blockHeight: Long = 0

    fun startCheck(): String? {
        if (initialChecks == checks) {
            this.blockHeight = connection.blockHeightFinal
        }

        if (checks-- <= 0) {
            return null
        }

        if (!isValid(blockHeight)) {
            logToSystem("BlockHeight is no longer valid $blockHeight", 1)
            return null
        }

        val status = connection.getSignatureStatuses(listOf(hash), false)

        if (status == null || status.isEmpty || status[0].isJsonNull) {
            delay(500)
            return startCheck() // Add return to terminate recursion
        }

        val response = status[0].asJsonObject

        if (response["confirmationStatus"].isJsonNull) {
            delay(500)
            return startCheck() // Add return to terminate recursion
        }

        val confirmationStatus = response["confirmationStatus"].asString

        if (confirmationStatus == "processed") {
            delay(500)
            return startCheck() // Add return to terminate recursion
        }

        if (confirmationStatus == "finalized" || confirmationStatus == "confirmed") {
            return confirmationStatus
        }

        return null
    }

    fun isValid(height: Long): Boolean {
        val blockHeight = connection.blockHeight
        return (blockHeight > (height - 150))
    }
}
