package io.sol.account

import io.sol.account.wallet.Wallet
import io.utils.logger.Logger.logToSystem
import lombok.Getter
import org.sol4k.Keypair

@Getter
class Account {
    private val wallet = Wallet(Keypair.generate())
    private val extendedWallets: MutableCollection<Wallet> = LinkedHashSet()

    fun generateWallets(size: Int, wipe: Boolean) {
        if (extendedWallets.size >= size) if (!wipe) logToSystem("Extended Wallets are already at this size", 1)
        else extendedWallets.clear()

        for (i in 0 until size) {
           extendedWallets.add(Wallet(Keypair.generate()))
        }

        logToSystem("Extended Wallets have been generated", 0)
    }
}
