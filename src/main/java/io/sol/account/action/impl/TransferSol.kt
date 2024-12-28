package io.sol.account.action.impl

import io.sol.account.action.TransferAction
import io.sol.connection.Connection
import io.sol.types.Commitment
import io.utils.sol.SolanaConstants.LAMPORTS_PER_SOL
import lombok.Getter
import org.sol4k.Keypair
import org.sol4k.PublicKey
import org.sol4k.TransactionMessage
import org.sol4k.VersionedTransaction
import org.sol4k.instruction.TransferInstruction

/**
 * Ease to use action that can transfer SOL
 *
 * String signature = new TransferSol(KEYPAIR, PUBLICKEY, AMOUNT, COMMITMENT, CONNECTION).execute();
 */
@Getter
class TransferSol(
    private val transferFrom: Keypair?,
    private val transferTo: PublicKey?,
    private val amount: Long,
    private val commitment: Commitment?,
    private val connection: Connection?,
    base: String?,
    type: String?
) : TransferAction(base!!, type!!) {
    constructor(
        from: Keypair, to: String?, amount: Long, commitment: Commitment, connection: Connection,
        base: String?, type: String?
    ) : this(from, PublicKey(to!!), amount, commitment, connection, base, type)

    constructor(
        from: Keypair?, to: String?, amount: Double, commitment: Commitment?, connection: Connection?,
        base: String?, type: String?
    ) : this(from, PublicKey(to!!), (amount * LAMPORTS_PER_SOL).toLong(), commitment, connection, base, type)

    constructor(
        from: Keypair?, to: PublicKey?, amount: Double, commitment: Commitment?, connection: Connection?,
        base: String?, type: String?
    ) : this(from, to, (amount * LAMPORTS_PER_SOL).toLong(), commitment, connection, base, type)

    constructor(
        from: Keypair?,
        to: PublicKey?,
        amount: Double,
        commitment: Commitment?,
        connection: Connection?
    ) : this(from, to, (amount * LAMPORTS_PER_SOL).toLong(), commitment, connection, "64", "legacy")

    constructor(from: Keypair?, to: String?, amount: Double, commitment: Commitment?, connection: Connection?) : this(
        from, PublicKey(
            to!!
        ), (amount * LAMPORTS_PER_SOL).toLong(), commitment, connection, "64", "legacy"
    )

    override fun send64Legacy(): String? {
        val instruction = transferFrom?.let {
            TransferInstruction(
                it.publicKey,
                transferTo!!,
                amount
            )
        }

        val blockhash = connection?.latestBlockHash

        val message = instruction?.let {
            transferFrom?.let { it1 ->
                TransactionMessage.newMessage(
                    it1.publicKey,
                    blockhash!!,
                    it
                )
            }
        }

        val transaction = message?.let { VersionedTransaction(it) }

        if (transferFrom != null) {
            transaction?.sign(transferFrom)
        }

        return transaction?.let { connection?.sendTransaction64(it) }
    }

    override fun send58Legacy(): String? {
        val instruction = transferFrom?.let {
            TransferInstruction(
                it.publicKey,
                transferTo!!,
                amount
            )
        }

        val blockhash = connection?.latestBlockHash

        val message = transferFrom?.publicKey?.let {
            TransactionMessage.newMessage(
                it,
                blockhash!!,
                instruction!!
            )
        }

        val transaction = message?.let { VersionedTransaction(it) }

        if (transferFrom != null) {
            transaction?.sign(transferFrom)
        }

        return transaction?.let { connection?.sendTransaction58(it) }
    }

    override fun send64Raw(): String? {
        val instruction = transferFrom?.let {
            TransferInstruction(
                it.publicKey,
                transferTo!!,
                amount
            )
        }

        val blockhash = connection?.latestBlockHash

        val message = transferFrom?.let {
            TransactionMessage.newMessage(
                it.publicKey,
                blockhash!!,
                instruction!!
            )
        }

        val transaction = message?.let { VersionedTransaction(it) }

        if (transferFrom != null) {
            transaction?.sign(transferFrom)
        }

        return transaction?.let { connection?.sendRawTransaction64(it) }
    }

    override fun send58Raw(): String? {
        val instruction = transferFrom?.let {
            TransferInstruction(
                it.publicKey,
                transferTo!!,
                amount
            )
        }

        val blockhash = connection?.latestBlockHash

        val message = transferFrom?.let {
            TransactionMessage.newMessage(
                it.publicKey,
                blockhash!!,
                instruction!!
            )
        }

        val transaction = message?.let { VersionedTransaction(it) }

        if (transferFrom != null) {
            transaction?.sign(transferFrom)
        }

        return transaction?.let { connection?.sendRawTransaction58(it) }
    }
}
