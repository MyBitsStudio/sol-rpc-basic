package io.sol.api.impl

object RPCRequests {
    var ACCOUNT_INFO: String = "getAccountInfo"
    @JvmField
    var GET_BALANCE: String = "getBalance"
    var GET_BLOCK: String = "getBlock"
    var GET_BLOCK_COMMITMENT: String = "getBlockCommitment"
    var GET_BLOCK_HEIGHT: String = "getBlockHeight"
    var GET_BLOCK_PRODUCTION: String = "getBlockProduction"
    var GET_BLOCK_TIME: String = "getBlockTime"
    var GET_BLOCKS: String = "getBlocks"
    var GET_BLOCKS_WITH_LIMIT: String = "getBlocksWithLimit"
    var GET_CLUSTER_NODES: String = "getClusterNodes"
    var GET_EPOCH_INFO: String = "getEpochInfo"
    var GET_EPOCH_SCHEDULE: String = "getEpochSchedule"
    @JvmField
    var GET_FEE_FOR_MESSAGE: String = "getFeeForMessage"
    @JvmField
    var GET_FIRST_AVAILABLE_BLOCK: String = "getFirstAvailableBlock"
    var GET_GENESIS_HASH: String = "getGenesisHash"
    @JvmField
    var GET_HEALTH: String = "getHealth"
    var GET_HIGHEST_SNAPSHOT_SLOT: String = "getHighestSnapshotSlot"
    var GET_IDENTITY: String = "getIdentity"
    var GET_INFLATION_GOVERNOR: String = "getInflationGovernor"
    var GET_INFLATION_RATE: String = "getInflationRate"
    var GET_INFLATION_REWARD: String = "getInflationReward"
    var GET_LARGEST_ACCOUNTS: String = "getLargestAccounts"
    @JvmField
    var GET_LATEST_BLOCKHASH: String = "getLatestBlockhash"
    @JvmField
    var GET_EXTENDED_BLOCKHASH: String = "getLatestBlockhash"
    var GET_LEADER_SCHEDULE: String = "getLeaderSchedule"
    var GET_MAX_RETRANSMIT_SLOT: String = "getMaxRetransmitSlot"
    var GET_MAX_SHRED_INSERT_SLOTS: String = "getMaxShredInsertSlots"
    @JvmField
    var GET_MIN_BALANCE_FOR_RENT_EXEMPTION: String = "getMinimumBalanceForRentExemption"
    @JvmField
    var GET_MULTIPLE_ACCOUNTS: String = "getMultipleAccounts"
    var GET_PROGRAM_ACCOUNTS: String = "getProgramAccounts"
    var GET_RECENT_PERFORMANCE_SAMPLES: String = "getRecentPerformanceSamples"
    var GET_RECENT_PRIORITIZATION_FEES: String = "getRecentPrioritizationFees"
    @JvmField
    var GET_SIGNATURE_STATUSES: String = "getSignatureStatuses"
    @JvmField
    var GET_SIGNATURE_FOR_ADDRESS: String = "getSignaturesForAddress"
    @JvmField
    var GET_SLOT: String = "getSlot"
    var GET_SLOT_LEADER: String = "getSlotLeader"
    var GET_SLOT_LEADERS: String = "getSlotLeaders"
    var GET_STAKE_MINIMUM_DELEGATION: String = "getStakeMinimumDelegation"
    var GET_SUPPLY: String = "getSupply"
    var GET_TOKEN_ACCOUNT_BALANCE: String = "getTokenAccountBalance"
    var GET_TOKEN_ACCOUNTS_BY_DELEGATE: String = "getTokenAccountsByDelegate"
    @JvmField
    var GET_TOKEN_ACCOUNTS_BY_OWNER: String = "getTokenAccountsByOwner"
    @JvmField
    var GET_TOKEN_LARGEST_ACCOUNTS: String = "getTokenLargestAccounts"
    @JvmField
    var GET_TOKEN_SUPPLY: String = "getTokenSupply"
    @JvmField
    var GET_TRANSACTION: String = "getTransaction"
    var GET_TRANSACTION_COUNT: String = "getTransactionCount"
    var GET_VERSION: String = "getVersion"
    var GET_VOTE_ACCOUNTS: String = "getVoteAccounts"
    @JvmField
    var IS_BLOCKHASH_VALID: String = "isBlockhashValid"
    var MINIMUM_LEDGER_SLOT: String = "minimumLedgerSlot"
    @JvmField
    var REQUEST_AIRDROP: String = "requestAirdrop"
    @JvmField
    var SEND_TRANSACTION: String = "sendTransaction"
    @JvmField
    var SEND_RAW_TRANSACTION: String = "sendTransaction"
    @JvmField
    var SIMULATE_TRANSACTION: String = "simulateTransaction"
}
