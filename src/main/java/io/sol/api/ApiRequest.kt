package io.sol.api

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import lombok.Getter
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*
import java.util.concurrent.ExecutionException

@Getter
class ApiRequest(private val url: String, private val method: String) {
    private var body: String? = null

    private var response: JsonObject? = null
    private var request: JsonObject? = null
    private val headers = JsonObject()

    private val gson = Gson()

    fun addHeader(key: String?, value: String?): ApiRequest {
        headers.addProperty(key, value)
        return this
    }

    fun setBody(body: String?): ApiRequest {
        this.body = body
        return this
    }

    fun retrieveResponse(): JsonObject? {
        return this.response
    }

    private fun build() {
        request = JsonObject()

        request!!.addProperty("url", url)
        request!!.addProperty("method", method)
        request!!.addProperty("body", body)
    }

    fun send(): JsonObject? {
        build()

        if (!verifyRequest()) {
            if (Objects.nonNull(request)) {
                request!!.addProperty("error", "Request is invalid")
            }
            return request
        }

        response = JsonObject()

        response!!.addProperty("requestTime", System.currentTimeMillis())
        response!!.addProperty("method", method)

        val preResponse: String = when (method) {
            "GET" -> requestGet()
            "POST", "PUT" -> requestPost()
            "NONE" -> requestUrl()
            else -> "null"
        }

        response!!.addProperty("responseTime", System.currentTimeMillis())

        if (Objects.isNull(preResponse) || preResponse.isEmpty() || preResponse == "null") {
            response!!.addProperty("error", "Response is null")
            return response
        }

        response!!.addProperty("response", gson.fromJson(preResponse, JsonObject::class.java).toString())

        return response
    }

    fun sendPrimitive(): JsonObject? {
        build()

        if (!verifyRequest()) {
            if (Objects.nonNull(request)) {
                request!!.addProperty("error", "Request is invalid")
            }
            return request
        }

        response = JsonObject()

        response!!.addProperty("requestTime", System.currentTimeMillis())
        response!!.addProperty("method", method)

        val preResponse: String = when (method) {
            "GET" -> requestGet()
            "POST", "PUT" -> requestPost()
            "NONE" -> requestUrl()
            else -> "null"
        }

        response!!.addProperty("responseTime", System.currentTimeMillis())

        if (Objects.isNull(preResponse) || preResponse.isEmpty() || preResponse == "null") {
            response!!.addProperty("error", "Response is null")
            return response
        }

        response!!.addProperty("response", gson.fromJson(preResponse, JsonPrimitive::class.java).toString())

        return response
    }

    fun sendArray(): JsonObject? {
        build()

        if (!verifyRequest()) {
            if (Objects.nonNull(request)) {
                request!!.addProperty("error", "Request is invalid")
            }
            return request
        }

        response = JsonObject()

        response!!.addProperty("requestTime", System.currentTimeMillis())
        response!!.addProperty("method", method)

        val preResponse: String = when (method) {
            "GET" -> requestGet()
            "POST", "PUT" -> requestPost()
            "NONE" -> requestUrl()
            else -> "null"
        }

        response!!.addProperty("responseTime", System.currentTimeMillis() )

        if (Objects.isNull(preResponse) || preResponse.isEmpty() || preResponse == "null") {
            response!!.addProperty("error", "Response is null")
            return response
        }

        response!!.addProperty("response", gson.fromJson(preResponse, JsonArray::class.java).toString())

        return response
    }

    private fun requestPost(): String {
        try {
            HttpClient.newHttpClient().use { client ->
                val request = HttpRequest.newBuilder()
                    .uri(URI.create(url.trim { it <= ' ' }))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(this.body))
                    .build()
                val futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                return futureResponse.get().body()
            }
        } catch (e: ExecutionException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    private fun requestGet(): String {
        try {
            HttpClient.newHttpClient().use { client ->
                val request = HttpRequest.newBuilder()
                    .uri(URI.create(this.url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .GET()
                    .build()
                val futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                return futureResponse.get().body()
            }
        } catch (e: ExecutionException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    private fun requestUrl(): String {
        try {
            HttpClient.newHttpClient().use { client ->
                val request = HttpRequest.newBuilder()
                    .uri(URI.create(this.url))
                    .build()
                val futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                return futureResponse.get().body()
            }
        } catch (e: ExecutionException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    private fun verifyRequest(): Boolean {
        if (Objects.isNull(request)) {
            return false
        }

        if (Objects.isNull(url)) {
            request!!.addProperty("error", "URL is null")
            return false
        }

        if (Objects.isNull(method)) {
            request!!.addProperty("error", "Method is null")
            return false
        }

        if (method == "POST" || method == "PUT") {
            if (Objects.isNull(body)) {
                request!!.addProperty("error", "Body is null")
                return false
            }
        }
        return true
    }

    override fun toString(): String {
        return "curl -X $method $url -H 'Content-Type: application/json' -d '$body'"
    }

    companion object {
        fun GET(url: String): ApiRequest {
            return ApiRequest(url, "GET")
        }

        fun POST(url: String): ApiRequest {
            return ApiRequest(url, "POST")
        }

        fun PUT(url: String): ApiRequest {
            return ApiRequest(url, "PUT")
        }

        fun NONE(url: String): ApiRequest {
            return ApiRequest(url, "NONE")
        }
    }
}
