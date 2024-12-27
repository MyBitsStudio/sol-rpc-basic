package io.sol.tool

import io.sol.connection.Connection

class BalanceChecker(
    private val connection: Connection,
    private val address: String,
    private val token: Boolean,
    private val mint: String,
    private var counts: Int
) {
    fun pollBalance(): Long {
        if (counts-- <= 0) {
            println("$address has polled for balance. return")
            return 0
        }

        try {
            Thread.sleep(500)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!token) {
            val balance = connection.getBalance(address)

            if (balance <= 0) {
                return pollBalance()
            }

            return balance
        } else {
            val amount = connection.getTokenAmountByOwner(address, mint)

            if (amount <= 0) {
                return pollBalance()
            }

            return amount
        }
    }
}
