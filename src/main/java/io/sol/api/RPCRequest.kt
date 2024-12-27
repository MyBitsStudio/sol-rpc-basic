package io.sol.api

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.sol.api.impl.RPCRequests
import io.sol.connection.Connection
import io.utils.logger.Logger

class RPCRequest(private val connection: Connection, private val rpcRequest: String) {
    private var apiRequest: ApiRequest? = null
    private val jsonElements: List<JsonElement> = ArrayList()

    var rpcResponse: JsonObject? = null
        private set
    private val requestArray = JsonArray()

    constructor(connection: Connection, rpcRequest: String, params: List<String?>) : this(connection, rpcRequest) {
        for (param in params) {
            requestArray.add(param)
        }
    }

    constructor(connection: Connection, rpcRequest: RPCRequests) : this(connection, rpcRequest.toString())

    constructor(connection: Connection, rpcRequest: String, param: String?) : this(connection, rpcRequest) {
        requestArray.add(param)
    }

    constructor(connection: Connection, rpcRequest: String, element: JsonElement?) : this(connection, rpcRequest) {
        requestArray.add(element)
    }

    constructor(connection: Connection, rpcRequest: String, element: Int) : this(connection, rpcRequest) {
        requestArray.add(element)
    }

    fun addParam(element: JsonElement?): RPCRequest {
        requestArray.add(element)
        return this
    }

    fun addParam(element: String?): RPCRequest {
        requestArray.add(element)
        return this
    }

    fun addParam(element: Int): RPCRequest {
        requestArray.add(element)
        return this
    }

    fun addParam(element: Long): RPCRequest {
        requestArray.add(element)
        return this
    }

    fun buildRequest() {
        val jsonObject = JsonObject()

        jsonObject.addProperty("jsonrpc", "2.0")
        jsonObject.addProperty("id", 1)
        jsonObject.addProperty("method", rpcRequest)

        if (!requestArray.isEmpty) {
            // System.out.println("requestArray "+requestArray);
            jsonObject.add("params", requestArray)
        }

        this.apiRequest = ApiRequest(connection.url.toString(), "POST")
            .addHeader("Content-Type", "application/json")
            .setBody(jsonObject.toString())

        val response = apiRequest!!.send()

        //System.out.println("response "+response);
        if (response!!.has("error")) {
            Logger.logToSystem("Error 2: " + response["error"].asJsonObject["message"].asString, 2)
            return
            //throw new RuntimeException("Error: " + response.get("error").getAsJsonObject().get("message").getAsString());
        }

        if (response.has("response")) {
            val parse = JsonParser.parseString(response["response"].asString).asJsonObject
            if (parse.has("result")) {
                if (!parse["result"].isJsonObject) {
                    val redeem = JsonObject()
                    redeem.add("extracted", parse["result"])
                    this.rpcResponse = redeem
                    return
                }
                val result = parse["result"].asJsonObject
                if (result.has("value")) {
                    if (result["value"].isJsonObject) {
                        this.rpcResponse = result["value"].asJsonObject
                        //System.out.println("final response "+this.response);
                    } else {
                        val respond = JsonObject()
                        respond.add("extracted", result["value"])
                        this.rpcResponse = respond
                    }
                } else {
                    this.rpcResponse = result
                }
            } else {
                val error = JsonObject()
                error.addProperty("error", "Result is invalid")
                this.rpcResponse = error
            }
        } else {
            val error = JsonObject()
            error.addProperty("error", "Response is invalid")
            this.rpcResponse = error
        }
    }

    fun hasError(): Boolean {
        return this.rpcResponse == null || rpcResponse!!.has("error")
    }

    val rpcResponseAsString: String
        get() = rpcResponse!!["extracted"].toString()

    fun getRpcResponse(key: String?): String {
        return rpcResponse!![key].asString
    }

    fun getRpcResponseInt(key: String?): Int {
        return rpcResponse!![key].asInt
    }

    fun getRpcResponseObject(key: String?): JsonObject {
        return rpcResponse!![key].asJsonObject
    }

    fun getRpcResponseArray(key: String?): JsonArray {
        return rpcResponse!![key].asJsonArray
    }

    fun getRpcResponseBoolean(key: String?): Boolean {
        return rpcResponse!![key].asBoolean
    }

    val extractedRpcResponseBoolean: Boolean
        get() = rpcResponse!!["extracted"].asBoolean

    fun getRpcResponseDouble(key: String?): Double {
        return rpcResponse!![key].asDouble
    }

    fun getRpcResponseLong(key: String?): Long {
        return rpcResponse!![key].asLong
    }

    val rpcResponseAsLong: Long
        get() = rpcResponse!!["extracted"].asLong

    val extractedRpcResponse: JsonObject
        get() = rpcResponse!!["extracted"].asJsonObject

    val extractedRpcResponseArray: JsonArray
        get() = rpcResponse!!["extracted"].asJsonArray

    fun getRpcResponseList(key: String?): List<String> {
        val list: MutableList<String> = ArrayList()
        val jsonArray = rpcResponse!![key].asJsonArray
        for (element in jsonArray) {
            list.add(element.asString)
        }
        return list
    }
}
