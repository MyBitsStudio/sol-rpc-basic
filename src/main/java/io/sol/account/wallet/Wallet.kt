package io.sol.account.wallet

import lombok.Getter
import org.sol4k.Keypair

@Getter
@JvmRecord
data class Wallet(val pair: Keypair)
