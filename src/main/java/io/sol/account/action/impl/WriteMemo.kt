package io.sol.account.action.impl

import io.utils.sol.SolanaConstants.MEMO_PROGRAM
import org.sol4k.*
import org.sol4k.Base58.decode
import org.sol4k.instruction.BaseInstruction
import org.sol4k.instruction.Instruction

class WriteMemo(private val memo: String, private val pair: Keypair, private val connection: Connection) {
    fun writeMemoInstruction(): Instruction {
        return BaseInstruction(
            decode(memo),
            listOf(
                AccountMeta(
                    pair.publicKey,
                    signer = true,
                    writable = true
                )
            ),
            PublicKey(MEMO_PROGRAM)
        )
    }

    fun writeMemoTransaction(): VersionedTransaction {
        return VersionedTransaction(
            TransactionMessage.newMessage(
                pair.publicKey,
                connection.getLatestBlockhash(),
                writeMemoInstruction()
            )
        )
    }
}