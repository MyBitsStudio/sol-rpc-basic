package io.sol.account.action.impl

import io.sol.account.action.TransferAction
import io.sol.connection.Connection
import io.sol.types.Commitment
import io.utils.logger.Logger.logToSystem
import lombok.Getter
import org.sol4k.*
import org.sol4k.instruction.SplTransferInstruction

@Getter
class TransferSPL @JvmOverloads constructor(
    private val sender: Keypair, private val receiver: PublicKey, private val mint: String, amount: Long,
    commitment: Commitment, connection: Connection, base: String? = "64", type: String? = "legacy"
) : TransferAction(
    base!!, type!!
) {
    private var amount: Long = -1
    private val commitment: Commitment
    private val connection: Connection

    init {
        this.amount = amount
        this.commitment = commitment
        this.connection = connection
    }

    constructor(
        sender: Keypair, receiver: PublicKey, mint: String, amount: Double,
        commitment: Commitment, connection: Connection, base: String?, type: String?
    ) : this(sender, receiver, mint, -1, commitment, connection, base, type) {
        this.amount = analyzeAmount(amount)
    }

    constructor(
        sender: Keypair, receiver: String?, mint: String, amount: Double,
        commitment: Commitment, connection: Connection, base: String?, type: String?
    ) : this(
        sender, PublicKey(
            receiver!!
        ), mint, -1, commitment, connection, base, type
    ) {
        this.amount = analyzeAmount(amount)
    }

    constructor(
        sender: Keypair, receiver: String?, mint: String, amount: Long,
        commitment: Commitment, connection: Connection, base: String?, type: String?
    ) : this(
        sender, PublicKey(
            receiver!!
        ), mint, amount, commitment, connection, base, type
    )

    constructor(
        sender: Keypair, receiver: String?, mint: String, amount: Double,
        commitment: Commitment, connection: Connection
    ) : this(sender, PublicKey(receiver!!), mint, -1, commitment, connection, "64", "legacy") {
        this.amount = analyzeAmount(amount)
    }

    constructor(
        sender: Keypair, receiver: String?, mint: String, amount: Long,
        commitment: Commitment, connection: Connection
    ) : this(sender, PublicKey(receiver!!), mint, amount, commitment, connection, "64", "legacy")

    constructor(
        sender: Keypair, receiver: PublicKey, mint: String, amount: Double,
        commitment: Commitment, connection: Connection
    ) : this(sender, receiver, mint, -1, commitment, connection, "64", "legacy") {
        this.amount = analyzeAmount(amount)
    }

    private fun analyzeAmount(amount: Double): Long {
        val decimals = connection.getTokenDecimals(mint)

        if (decimals == -1) {
            logToSystem("[TransferSPL] Invalid mint address", 2)
            return -1
        }

        return (amount * (10 xor decimals)).toLong()
    }

    override fun send64Legacy(): String? {
        if (amount == -1L) {
            return null
        }

        val decimals = connection.getTokenDecimals(mint)

        if (decimals == -1) {
            return null
        }

        val addresses: ProgramDerivedAddress = PublicKey.findProgramDerivedAddress(
            sender.publicKey,
            PublicKey(mint)
        )

        val instruction = SplTransferInstruction(
            addresses.publicKey,
            receiver,
            PublicKey(mint),
            sender.publicKey,
            amount,
            decimals
        )

        val blockhash = connection.latestBlockHash

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash!!,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        return connection.sendTransaction64(transaction)
    }

    override fun send58Legacy(): String? {
        if (amount == -1L) {
            return null
        }

        val decimals = connection.getTokenDecimals(mint)

        if (decimals == -1) {
            return null
        }

        val addresses: ProgramDerivedAddress = PublicKey.findProgramDerivedAddress(
            sender.publicKey,
            PublicKey(mint)
        )

        val instruction = SplTransferInstruction(
            addresses.publicKey,
            receiver,
            PublicKey(mint),
            sender.publicKey,
            amount,
            decimals
        )

        val blockhash = connection.latestBlockHash

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash!!,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        return connection.sendTransaction58(transaction)
    }

    override fun send64Raw(): String? {
        if (amount == -1L) {
            return null
        }

        val decimals = connection.getTokenDecimals(mint)

        if (decimals == -1) {
            return null
        }

        val addresses: ProgramDerivedAddress = PublicKey.findProgramDerivedAddress(
            sender.publicKey,
            PublicKey(mint)
        )

        val instruction = SplTransferInstruction(
            addresses.publicKey,
            receiver,
            PublicKey(mint),
            sender.publicKey,
            amount,
            decimals
        )

        val blockhash = connection.latestBlockHash

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash!!,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        return connection.sendRawTransaction64(transaction)
    }

    override fun send58Raw(): String? {
        if (amount == -1L) {
            return null
        }

        val decimals = connection.getTokenDecimals(mint)

        if (decimals == -1) {
            return null
        }

        val addresses: ProgramDerivedAddress = PublicKey.findProgramDerivedAddress(
            sender.publicKey,
            PublicKey(mint)
        )

        val instruction = SplTransferInstruction(
            addresses.publicKey,
            receiver,
            PublicKey(mint),
            sender.publicKey,
            amount,
            decimals
        )

        val blockhash = connection.latestBlockHash

        val message = TransactionMessage.newMessage(
            sender.publicKey,
            blockhash!!,
            instruction
        )

        val transaction = VersionedTransaction(message)

        transaction.sign(sender)

        return connection.sendRawTransaction58(transaction)
    }
}
