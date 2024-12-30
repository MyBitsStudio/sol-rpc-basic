package io.sol.tool

import io.sol.account.wallet.Wallet
import org.sol4k.Keypair

class VanityWalletGenerator(private val vanity: String, private val end: Boolean, private val strict: Boolean) :
    Runnable {
    override fun run() {
        var found = false
        var wallet: Wallet? = null
        while (!found) {
            wallet = Wallet(Keypair.generate())
            val address = wallet.pair.publicKey.toBase58()
            val index = vanity.length
            if (end) {
                val end = address.substring(address.length - index)
                if (strict) if (end == vanity) found = true
                else if (end.equals(vanity, ignoreCase = true)) found = true
            } else {
                val begin = address.substring(0, index)
                if (strict) if (begin == vanity) found = true
                else if (begin.equals(vanity, ignoreCase = true)) found = true
            }
        }

        println(
            """[VANITY] Wallet vanity has been found. 
 PublicKey : [${wallet!!.pair.publicKey.toBase58()}]
 PrivateKey: ${wallet.pair.secret.contentToString()}"""
        )
    }
}
