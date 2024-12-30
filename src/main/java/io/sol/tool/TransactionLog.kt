package io.sol.tool

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.sol.connection.Connection

class TransactionLog(private val signature: String, private val connection: Connection) {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    val transactionData: JsonObject?
        get() = connection.getTransaction(signature)
}
