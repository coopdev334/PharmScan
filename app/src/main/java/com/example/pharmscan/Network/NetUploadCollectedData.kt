package com.example.pharmscan.Network

import android.app.IntentService
import android.util.Log
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket
import android.content.Intent
import androidx.lifecycle.ViewModel


class NetworkThread : ViewModel(){

    suspend fun handleIncomingData(hostName : String?, hostPassword : String?, port: Int, data : String?) {

        try {
            val queryProcessorSocket: Socket = Socket(hostName, port)
            val queryProcessorDos =
                DataOutputStream(queryProcessorSocket.getOutputStream())
            val queryProcessorReader =
                BufferedReader(InputStreamReader(queryProcessorSocket.getInputStream()))
            queryProcessorDos.writeBytes( """ $data""".trimIndent())
            val response = queryProcessorReader.readLine()
            Log.d("TESTING NETWORK", response)
        }
        catch (Error: Exception) { Error.printStackTrace() }
    }

}
