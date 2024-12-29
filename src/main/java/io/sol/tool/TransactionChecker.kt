package io.sol.tool

import io.sol.connection.Connection
import java.util.List

class TransactionChecker(private val connection: Connection, private val hash: String, private var checks: Int) {
    fun startCheck(): String? {
        if (checks-- <= 0) {
            return null
        }

        val status = connection.getSignatureStatuses(listOf(hash), false)

        if (status == null || status.isEmpty || status[0].isJsonNull) {
            try {
                Thread.sleep(1000) // Add delay
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            return startCheck() // Add return to terminate recursion
        }

        val response = status[0].asJsonObject

        if (response["confirmationStatus"].isJsonNull) {
            try {
                Thread.sleep(1000) // Add delay
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            return startCheck() // Add return to terminate recursion
        }

        val confirmationStatus = response["confirmationStatus"].asString

        if (confirmationStatus == "processed") {
            try {
                Thread.sleep(1000) // Add delay
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            return startCheck() // Add return to terminate recursion
        }

        if (confirmationStatus == "finalized" || confirmationStatus == "confirmed") {
            return confirmationStatus
        }

        return null
    }
}
