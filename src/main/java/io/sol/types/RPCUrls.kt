package io.sol.types

import lombok.Getter

@Getter
enum class RPCUrls(val url: String) {
    MAINNET("https://api.mainnet-beta.solana.com/"),
    TESTNET("https://api.testnet.solana.com/ "),
    DEVNET("https://api.devnet.solana.com/"),
}
