package io.sol.tool

import io.sol.connection.Connection
import io.utils.logger.Logger.logToSystem
import lombok.Getter
import org.sol4k.Keypair
import org.sol4k.TransactionMessage
import org.sol4k.VersionedTransaction
import org.sol4k.instruction.Instruction

@Getter
class TransactionResender(
    private val connection: Connection,
    private val instruction: Instruction,
    private val sender: Keypair,
    private val intensity: Int,
    private var checks: Int
) {
    private var startTime: Long = 0
    private var endTime: Long = 0

    private val initialChecks = checks

    fun sendTransactionLegacy64(): String? {
        if (initialChecks == checks) {
            this.startTime = System.currentTimeMillis()
            this.endTime = ((1000 * 5) * (if (intensity % 2 == 0) intensity * 3L else intensity * 2L))
        } else {
            if (this.endTime <= System.currentTimeMillis()) {
                logToSystem("Transaction failed sending.", 2)
                return null
            }
        }


        val blockhash = connection.extendedBlockhash ?: return null

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash["blockhash"].asString,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        val signature = connection.sendTransaction64(transaction)

        val checker = TransactionChecker(
            connection,
            signature!!,
            10
        )

        val checked = checker.startCheck()

        if (checked == null) {
            if (--checks <= 0) {
                return null
            }
            return sendTransactionLegacy64()
        } else if (checked == "finalized" || checked == "confirmed") {
            return signature
        }

        return null
    }

    fun sendTransactionLegacy58(): String? {
        if (initialChecks == checks) {
            this.startTime = System.currentTimeMillis()
            this.endTime = ((1000 * 5) * (if (intensity % 2 == 0) intensity * 3L else intensity * 2L))
        } else {
            if (this.endTime <= System.currentTimeMillis()) {
                logToSystem("Transaction failed sending.", 2)
                return null
            }
        }


        val blockhash = connection.extendedBlockhash ?: return null

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash["blockhash"].asString,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        val signature = connection.sendTransaction58(transaction)

        val checker = TransactionChecker(
            connection,
            signature!!,
            10
        )

        val checked = checker.startCheck()

        if (checked == null) {
            if (--checks <= 0) {
                return null
            }
            return sendTransactionLegacy58()
        } else if (checked == "finalized" || checked == "confirmed") {
            return signature
        }

        return null
    }

    fun sendTransactionRaw64(): String? {
        if (initialChecks == checks) {
            this.startTime = System.currentTimeMillis()
            this.endTime = ((1000 * 5) * (if (intensity % 2 == 0) intensity * 3L else intensity * 2L))
        } else {
            if (this.endTime <= System.currentTimeMillis()) {
                logToSystem("Transaction failed sending.", 2)
                return null
            }
        }


        val blockhash = connection.extendedBlockhash ?: return null

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash["blockhash"].asString,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        val signature = connection.sendRawTransaction64(transaction)

        val checker = TransactionChecker(
            connection,
            signature!!,
            10
        )

        val checked = checker.startCheck()

        if (checked == null) {
            if (--checks <= 0) {
                return null
            }
            return sendTransactionRaw64()
        } else if (checked == "finalized" || checked == "confirmed") {
            return signature
        }

        return null
    }

    fun sendTransactionRaw58(): String? {
        if (initialChecks == checks) {
            this.startTime = System.currentTimeMillis()
            this.endTime = ((1000 * 5) * (if (intensity % 2 == 0) intensity * 3L else intensity * 2L))
        } else {
            if (this.endTime <= System.currentTimeMillis()) {
                logToSystem("Transaction failed sending.", 2)
                return null
            }
        }


        val blockhash = connection.extendedBlockhash ?: return null

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash["blockhash"].asString,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        val signature = connection.sendRawTransaction58(transaction)

        val checker = TransactionChecker(
            connection,
            signature!!,
            10
        )

        val checked = checker.startCheck()

        if (checked == null) {
            if (--checks <= 0) {
                return null
            }
            return sendTransactionRaw58()
        } else if (checked == "finalized" || checked == "confirmed") {
            return signature
        }

        return null
    }
}
