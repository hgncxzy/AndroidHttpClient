package com.xzy.androidhttpclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_test_get.setOnClickListener {
            runBlocking {
                val job = GlobalScope.async {
                    testDevicesAPIGet()
                }
                tv_result.text = job.await()
            }
        }
        btn_test_post.setOnClickListener {
            runBlocking {
                val job = GlobalScope.async {
                    testDevicesAPIPost(true)
                }
                tv_result.text = job.await()
            }
        }
        btn_clear.setOnClickListener {
            tv_result.text = ""
        }
    }

    private fun testDevicesAPIGet(): String {
        var requestResult = ""
        try {
            val requestUrl = "http://10.0.2.2:6302/devices?id=1"
            val url = URL(requestUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5 * 1000
            conn.readTimeout = 5 * 1000
            conn.requestMethod = "GET"
            conn.connect()
            if (conn.responseCode == 200) {
                // Receive response
                val reader = BufferedReader(InputStreamReader(conn.inputStream))
                var line: String?
                val result = StringBuffer("")
                while (reader.readLine().also { line = it } != null) {
                    line = URLDecoder.decode(line, "utf-8")
                    result.append(line)
                }
                reader.close()
                Log.e(tag, "Request result--->$result")
                requestResult = result.toString()
            } else {
                val str =
                    "Request failed ,responseCode = ${conn.responseCode},responseMsg = ${conn.responseMessage}"
                Log.e(tag, str)
                requestResult = str
            }
            conn.disconnect()
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            requestResult = e.toString()
        }
        return requestResult
    }

    private fun testDevicesAPIPost(useJson: Boolean): String {
        var requestResult = ""
        try {
            val requestUrl = "http://10.0.2.2:6302/devices"
            val url = URL(requestUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5 * 1000
            conn.readTimeout = 5 * 1000
            conn.requestMethod = "POST"
            if (useJson) {
                conn.setRequestProperty("Content-Type", "application/json")
            }
            conn.connect()
            if (!useJson) {
                // Use form format
                val data = "id=1"
                conn.outputStream.write(data.toByteArray())
            } else {
                // Use JSON format
                val out = DataOutputStream(conn.outputStream)
                val obj = JSONObject()
                obj.put("id", "1")
                out.writeBytes(obj.toString())
                out.flush()
                out.close()
            }
            if (conn.responseCode == 200) {
                // Receive response
                val reader = BufferedReader(InputStreamReader(conn.inputStream))
                var line: String?
                val result = StringBuffer("")
                while (reader.readLine().also { line = it } != null) {
                    line = URLDecoder.decode(line, "utf-8")
                    result.append(line)
                }
                reader.close()
                Log.e(tag, "Request result--->$result")
                requestResult = result.toString()
            } else {
                val str =
                    "Request failed ,responseCode = ${conn.responseCode},responseMsg = ${conn.responseMessage}"
                Log.e(tag, str)
                requestResult = str
            }
            conn.disconnect()
        } catch (e: Exception) {
            Log.e(tag, e.toString())
            requestResult = e.toString()
        }
        return requestResult
    }
}