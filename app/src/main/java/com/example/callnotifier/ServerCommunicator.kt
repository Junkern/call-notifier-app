package com.example.callnotifier
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ServerCommunicator(val port: Int = 5678) {
    private val client: OkHttpClient = OkHttpClient()

    companion object {
        const val DEFAULT_IP = "192.168.0.157"
        val TAG = ServerCommunicator::class.java.simpleName
        val json = "application/json; charest=utf-8".toMediaType()
    }

    suspend fun ping(number: String, ip: String = DEFAULT_IP) {
        withContext(Dispatchers.IO) {
            Log.i(TAG, "ping $ip at port 5678 with number $number")
            val body = "{\"message\": \"$number\"}".toRequestBody(json)
            val request = Request.Builder().url("http://$ip:5678/notification").post(body).build()
            client.newCall(request).execute()
        }
    }
}