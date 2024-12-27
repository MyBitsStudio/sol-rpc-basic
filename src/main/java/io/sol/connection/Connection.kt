package io.sol.connection

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.sol.api.RPCRequest
import io.sol.api.impl.RPCRequests
import io.sol.types.Commitment
import io.sol.types.RPCUrls
import io.utils.logger.Logger
import lombok.Getter
import org.jetbrains.annotations.Contract
import org.sol4k.TransactionMessage
import org.sol4k.VersionedTransaction
import java.util.*

/**
 * A rescaled implementation of the Solana RPC API's
 *
 *
 * This is an upgraded connection for RPCs, to overcome the shortcomings of other tools
 *
 */
@Getter
class Connection(url: String?, commitment: Commitment?) {
    val url: String?

    private val commitment: Commitment?

    private val gson = Gson()
    private var rpcRequest: RPCRequest? = null

    private var options = JsonObject()

    init {
        assert(url != null)
        assert(commitment != null)
        this.url = url
        this.commitment = commitment
    }

    fun getBalance(publicKey: String): Long {
        rpcRequest = RPCRequest(this, RPCRequests.GET_BALANCE, java.util.List.of(publicKey))
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error getBalance (" + publicKey + ") : " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return 0
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsLong
    }

    val latestBlockHash: String?
        get() {
            rpcRequest = RPCRequest(this, RPCRequests.GET_LATEST_BLOCKHASH)
            rpcRequest!!.buildRequest()
            if (rpcRequest!!.hasError()) {
                Logger.logToSystem(
                    "Error getLatestBlockHash: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                        "error"
                    )), 2
                )
                return null
                //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
            }
            return rpcRequest!!.getRpcResponse("blockhash")
        }

    val extendedBlockhash: JsonObject?
        get() {
            rpcRequest = RPCRequest(this, RPCRequests.GET_EXTENDED_BLOCKHASH)
            rpcRequest!!.buildRequest()
            if (rpcRequest!!.hasError()) {
                Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
                //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
            }
            return rpcRequest!!.rpcResponse
        }

    fun getFeeForMessage(message: TransactionMessage): Long {
        options = JsonObject()
        options.addProperty("commitment", commitment.toString())
        val decoded = Base64.getEncoder().encodeToString(message.serialize())
        rpcRequest = RPCRequest(this, RPCRequests.GET_FEE_FOR_MESSAGE, decoded).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsLong
    }

    val firstAvailableBlock: Long
        get() {
            rpcRequest = RPCRequest(this, RPCRequests.GET_FIRST_AVAILABLE_BLOCK)
            rpcRequest!!.buildRequest()
            if (rpcRequest!!.hasError()) {
                Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
                //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
            }
            return rpcRequest!!.rpcResponseAsLong
        }

    val health: String
        get() {
            rpcRequest = RPCRequest(this, RPCRequests.GET_HEALTH)
            rpcRequest!!.buildRequest()
            if (rpcRequest!!.hasError()) {
                Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
                //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
            }
            return rpcRequest!!.rpcResponseAsString
        }

    fun getMinimumBalanceforRent(length: Int): Long {
        rpcRequest = RPCRequest(this, RPCRequests.GET_MIN_BALANCE_FOR_RENT_EXEMPTION, length)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsLong
    }

    fun getMultipleAccounts(accounts: List<String?>): JsonArray {
        val jsonArray = JsonArray()
        for (account in accounts) {
            jsonArray.add(account)
        }
        rpcRequest = RPCRequest(this, RPCRequests.GET_MULTIPLE_ACCOUNTS, jsonArray)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseArray
    }

    fun getSignatureStatuses(signatures: List<String>, transactionHistory: Boolean): JsonArray? {
        val jsonArray = JsonArray()
        for (signature in signatures) {
            jsonArray.add(signature.replace("\"".toRegex(), ""))
        }
        options = JsonObject()
        options.addProperty("searchTransactionHistory", transactionHistory)
        rpcRequest = RPCRequest(this, RPCRequests.GET_SIGNATURE_STATUSES, jsonArray).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error getSignatureStatues: " + (if (rpcRequest!!.rpcResponse == null) null else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return null
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseArray
    }

    fun getSignatureStatus(signature: String): JsonArray {
        options = JsonObject()
        options.addProperty("searchTransactionHistory", false)
        rpcRequest = RPCRequest(
            this,
            RPCRequests.GET_SIGNATURE_STATUSES,
            signature.replace("\"".toRegex(), "")
        ).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseArray
    }

    fun getSignaturesForAddress(address: String?, limit: Int): JsonArray {
        options = JsonObject()
        options.addProperty("limit", limit)
        rpcRequest = RPCRequest(this, RPCRequests.GET_SIGNATURE_FOR_ADDRESS, address).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseArray
    }

    val slot: Long
        get() {
            rpcRequest = RPCRequest(this, RPCRequests.GET_SLOT)
            rpcRequest!!.buildRequest()
            if (rpcRequest!!.hasError()) {
                Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
                //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
            }
            return rpcRequest!!.rpcResponseAsLong
        }

    fun getTokenAccountsByOwner(publicKey: String?): JsonArray? {
        options = JsonObject()
        options.addProperty("programId", "TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA")
        rpcRequest = RPCRequest(this, RPCRequests.GET_TOKEN_ACCOUNTS_BY_OWNER, publicKey).addParam(options)
        options = JsonObject()
        options.addProperty("encoding", "jsonParsed")
        rpcRequest!!.addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error getTokenAccountsByOwner: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return null
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }

        return rpcRequest!!.extractedRpcResponseArray
    }

    fun getTokenAmountByOwner(publicKey: String?, mint: String): Long {
        val accounts = getTokenAccountsByOwner(publicKey) ?: return 0

        for (account in accounts) {
            val accountObject = account.asJsonObject
            val data =
                accountObject["account"].asJsonObject["data"].asJsonObject["parsed"].asJsonObject["info"].asJsonObject
                    ?: return 0

            if (data["mint"].asString == mint) {
                val tokenAmount = data["tokenAmount"].asJsonObject
                return tokenAmount["amount"].toString().replace("\"".toRegex(), "").toLong()
            }
        }
        return 0
    }


    fun getAllTokenBalances(publicKey: String?): Map<String, Long> {
        val accounts = getTokenAccountsByOwner(publicKey)
        val balances: MutableMap<String, Long> = HashMap()
        for (account in accounts!!) {
            val accountObject = account.asJsonObject
            val data =
                accountObject["account"].asJsonObject["data"].asJsonObject["parsed"].asJsonObject["info"].asJsonObject
                    ?: throw RuntimeException("Error: Account data is null")
            val mint = data["mint"].asString
            val tokenAmount = data["tokenAmount"].asJsonObject
            balances[mint] = tokenAmount["amount"].toString().replace("\"".toRegex(), "").toLong()
        }
        return balances
    }

    fun getTokenAccountsByOwnerAndMint(publicKey: String?, mint: String?): JsonArray {
        options = JsonObject()
        options.addProperty("mint", mint)
        rpcRequest = RPCRequest(this, RPCRequests.GET_TOKEN_ACCOUNTS_BY_OWNER, publicKey).addParam(options)
        options = JsonObject()
        options.addProperty("encoding", "jsonParsed")
        rpcRequest!!.addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseArray
    }

    fun getTokenAccountsByOwnerAndProgram(publicKey: String?, programId: String?): JsonArray {
        options = JsonObject()
        options.addProperty("programId", programId)
        rpcRequest = RPCRequest(this, RPCRequests.GET_TOKEN_ACCOUNTS_BY_OWNER, publicKey).addParam(options)
        options = JsonObject()
        options.addProperty("encoding", "jsonParsed")
        rpcRequest!!.addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseArray
    }

    /**
     * Get the largest accounts for specified mint
     *
     *
     * @param mint String of the mint address
     * @return JsonArray of the largest accounts
     * @throws RuntimeException if the RPC request has an error
     */
    fun getTokenLargestAccounts(mint: String?): JsonArray {
        rpcRequest = RPCRequest(this, RPCRequests.GET_TOKEN_LARGEST_ACCOUNTS, mint)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseArray
    }

    /**
     * Get the token supply for the specified mint
     *
     *
     * @param mint String of the mint address
     * @return JsonObject of the token supply
     * @throws RuntimeException if the RPC request has an error
     */
    fun getTokenSupply(mint: String?): JsonObject? {
        rpcRequest = RPCRequest(this, RPCRequests.GET_TOKEN_SUPPLY, mint)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error getTokenSupply: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return null
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponse
    }

    /**
     * Uses the TokenSupply RPC method and pulls the decimals amount from it.
     *
     *
     * This method is a helper method to get the decimals of a token
     * by using the getTokenSupply method and parsing the decimals from it.
     * This method is useful for getting the decimals of a token without
     * having to call the getTokenSupply method and parsing the decimals
     * from the JsonObject.
     *
     * @param mint String of the mint address
     * @return int of the token decimals
     * @throws RuntimeException if the RPC request has an error
     */
    fun getTokenDecimals(mint: String?): Int {
        val tokenSupply = getTokenSupply(mint)
        return tokenSupply!!["decimals"].asString.replace("\"".toRegex(), "").toInt()
    }

    /**
     * Get the transaction details for the specified signature
     *
     *
     * @param signature String of the transaction signature
     * @return JsonObject of the transaction details
     * @throws RuntimeException if the RPC request has an error
     */
    fun getTransaction(signature: String?): JsonObject? {
        rpcRequest = RPCRequest(this, RPCRequests.GET_TRANSACTION, java.util.List.of(signature, "json"))
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponse
    }

    /**
     * Checks if the blockhash is valid
     *
     *
     * @param blockhash String of blockhash to check
     * @return boolean valid
     * @throws RuntimeException if the RPC request has an error
     */
    fun isBlockhashValid(blockhash: String?): Boolean {
        options = JsonObject()
        options.addProperty("commitment", commitment.toString())
        rpcRequest = RPCRequest(this, RPCRequests.IS_BLOCKHASH_VALID, blockhash).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.extractedRpcResponseBoolean
    }

    /**
     * Request an airdrop of lamports to the specified public key
     *
     *
     * (Currently Bugged ? Solana has problems)
     * @param publicKey Public Key String for requesting account
     * @param lamports Amount of lamports to request
     * @return Transaction Signature String
     * @throws RuntimeException if the RPC request has an error
     */
    fun requestAirdrop(publicKey: String?, lamports: Long): String {
        rpcRequest = RPCRequest(this, RPCRequests.REQUEST_AIRDROP, publicKey).addParam(lamports)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsString
    }

    fun sendTransaction(encodedTransaction: String?): String? {
        rpcRequest = RPCRequest(this, RPCRequests.SEND_TRANSACTION, encodedTransaction)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error sendTransaction: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return null
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsString
    }

    fun sendTransaction64(transaction: VersionedTransaction): String? {
        return sendMEVTransaction(transaction)
    }

    fun sendMEVTransaction(transaction: VersionedTransaction): String? {
        options = JsonObject()
        options.addProperty("skipPreflight", false)
        options.addProperty("preFlightCommitment", "finalized")
        options.addProperty("encoding", "base64")
        val encodedTransaction = Base64.getEncoder().encodeToString(transaction.serialize())
        rpcRequest = RPCRequest(this, RPCRequests.SEND_TRANSACTION, encodedTransaction).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error sendMEVTransaction: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return null
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsString
    }

    /**
     * Send a raw transaction to the network, skipping preflights and running on base64 encoding
     * @param encodedTransaction - Serialized and signed transaction to send
     * @return Signature of the transaction
     */
    fun sendRawTransaction64(encodedTransaction: String?): String? {
        options = JsonObject()
        options.addProperty("skipPreflight", true)
        options.addProperty("preFlightCommitment", "confirmed")
        options.addProperty("encoding", "base64")
        rpcRequest = RPCRequest(this, RPCRequests.SEND_RAW_TRANSACTION, encodedTransaction).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error RawTransaction64: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return null
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsString
    }

    /**
     * NOT RECOMMENDED - Use sendRawTransaction64 instead
     * Send a raw transaction to the network, skipping preflights and running on base58 encoding
     * @param encodedTransaction - Serialized and signed transaction to send
     * @return Signature of the transaction
     */
    @Deprecated("")
    fun sendRawTransaction58(encodedTransaction: String?): String? {
        options = JsonObject()
        options.addProperty("skipPreflight", true)
        options.addProperty("preFlightCommitment", commitment.toString())
        options.addProperty("encoding", "base58")
        options.addProperty("maxRetries", 5)
        rpcRequest = RPCRequest(this, RPCRequests.SEND_RAW_TRANSACTION, encodedTransaction).addParam(options)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem(
                "Error RawTransaction56: " + (if (rpcRequest!!.rpcResponse == null) "NULL" else rpcRequest!!.getRpcResponse(
                    "error"
                )), 2
            )
            return null
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponseAsString
    }

    fun simulateTransaction(encodedTransaction: String?): JsonObject? {
        rpcRequest = RPCRequest(this, RPCRequests.SIMULATE_TRANSACTION, encodedTransaction)
        rpcRequest!!.buildRequest()
        if (rpcRequest!!.hasError()) {
            Logger.logToSystem("Error: " + rpcRequest!!.getRpcResponse("error"), 2)
            //throw new RuntimeException("Error: " + rpcRequest.getRpcResponse("error"));
        }
        return rpcRequest!!.rpcResponse
    }

    companion object {
        @Contract("_, _ -> new")
        fun createConnection(url: RPCUrls, commitment: Commitment?): Connection {
            return Connection(url.url, commitment)
        }
    }
}
