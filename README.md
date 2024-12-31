SOL RPC FOR JAVA
---

**This is a development build. Currently in Alpha stages. You may run into errors, uncompleted functions, or badly written logic. They will be updated and finished before official release** 
---

SOL Basic RPC is an extension to Sol4K to relive missing functions and operations that are essential to larger scale builds. This hopes to rewrite most RPC functions, wallets, and provide needed tools with quick-ease operations that make large scale applications able to be developed using Java.

This is an extension of Sol4k, which is included in the build.

> **Sol4K** - https://sol4k.org/

---

> For information on how to install and use the library, visit our [Docs](https://mybitsstudio.github.io/sol-rpc-for-java-docs/#/)

## Examples

> We have included some examples on how to work with this and how to use this in a large scale operation.
> They can be found at the following location :
>
> https://github.com/MyBitsStudio/sol-rpc-basic-examples

## Current RPC Features

> We are trying to implement all Sol RPC features into the library to give more berth to projects wanting to build with Java.
> Those marked with ** are custom to help with overriding functions.
> 
> Working & Tested :
> ```
> getBalance (long) - Retrieves Lamports balance of PublicKey
> latestBlockHash (string) - Retrieves lastes blockchash
> extendedBlockhash (JsonObject) - Extended information of blockhash
> getBlockHeight (long) - Gets the current height of the block
> getBlockHeightFinal** (long) - Gets the current height of the block under Finalized commitment
> firstAvailableBlock (long) - Gets the first available block slot
> health (string) - Displays the health of the RPC
> getSignatureStatuses (JsonArray) - An array of confirmation statuses of signatures
> getSignatureStatus** (JsonArray) - Returns confirmation status of one signature
> slot (long) - The most current slot
> getTokenAccountsByOwner (JsonArray) - Displays balances of tokens of specified account
> getTokenAmountByOwner** (long) - Displays balance of minted token by account
> getAllTokenBalances** (Map<String, Long) - Displays all balances of tokens by account
> getTokenAccountsByOwnerAndMint (JsonArray) - returns accounts of owner that are associated with the mint
> getTokenSupply (JsonObject) - Information about the mint including tokenSupply
> getTokenDecimals** (int) - Gives the decimal amount for specified mint
> isBlockhashValid (boolean) - Returns if the blockhash is still valid
> sendTransaction (string) - Transaction signature of sent transaction
> sendTransaction64** (string) - Transaction signature of sent transaction on Base 64
> sendTransaction58** (string) - Transaction signature of sent transaction on Base 58
> sendRawTransaction64** (string) - Transaction signature of sent transaction bypassing preflight on Base 64
> sendRawTransaction58** (string) - Transaction signature of sent transaction bypassing preflight on Base 58
> simulateTransaction (JsonObject) - Simulates transaction and returns information about simulation
> getTransaction (JsonObject ) - Retrieves information about the transaction
> ```
> 
> Working & Non-Tested :
> ```
> getFeeForMessage (long) - Gets lamports fee for TransactionMessage
> getMinimumBalanceforRent (long) - Gets minimum lamports needed for rent
> getMultipleAccounts (JsonArray) - Displays information for collection of accounts
> getSignaturesForAddress (JsonArray) - A list of signatures associated with the address
> getTokenAccountsByOwnerAndProgram (JsonArray) - A list of accounts by owner and specified program
> getTokenLargestAccounts (JsonArray) - A list of the highest holders of the mint
> requestAirdrop (string) - Transaction signature of Airdrop
> ```
> 
> To Add :
>```
>getAccountInfo
>getBlock
>getBlockCommitment
>getBlockProduction
>getBlockTime
>getBlocks
>getBlocksWithLimit
>getClusterNodes
>getEpochInfo
>getEpochSchedule
>getGenesisHash
>getHighestSnapshotSlot
>getIdentity
>getInflationGovernor
>getInflationRate
>getInflationReward
>getLargestAccounts
>getLeaderSchedule
>getMaxRetransmitSlot
>getMaxShredInsertSlots
>getProgramAccounts
>getRecentPerformanceSamples
>getRecentPrioritizationFees
>getSlotLeader
>getSlotLeaders
>getStakeMinimumDelegation
>getSupply
>getTokenAccountBalance
>getTokenAccountsByDelegate
>getTransactionCount
>getVersion
>getVoteAccounts
>minimumLedgerSlot
>```

## SOL Enhanced Connection

> This is the free and basic RPC extension. There is a more enhanced, versatile, and diverse library that captures not only these, but
> captures the use of different programs and functions of the SOL blockchain.
> 
> Enhanced connection includes :
> > Jupiter Extension - Be able to swap, get price quotes, send payments, establish fees and collect on them, all from an easy to use library extension
>
> > Jito Extension - Be able to effortlessly and easily send Jito Bundles, manage tipping, and provide seemless integration with Jito
> 
> > Liquidity Extensions - Be able to swap, add liquidity, and check the market with multiple liquidity providers
> 
> > Provider Extensions - Be able to use QuickNode, Helius, and Triton exclusive API's
>
> > Token Tools - Launch, inspect, and jump into creating tokens and NFT's within the SOL ecosystem
> 
> To learn more about Sol Enhanced Connection, please contact : https://t.me/QuantumComputed

