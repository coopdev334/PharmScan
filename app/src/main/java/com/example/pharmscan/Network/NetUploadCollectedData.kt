package com.example.pharmscan.Network

import android.app.IntentService
import android.util.Log
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket
import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetAddress


class NetworkThread : ViewModel(){

    fun handleIncomingData(hostName : String?, hostPassword : String?, port: Int, data : String?) = CoroutineScope(
        Dispatchers.IO).launch {

        try {
            /*val address: InetAddress = InetAddress.getByName("MATT-DESKTOP")
            if (address != null) Log.w(
                "DEBUG",
                address.getHostAddress().toString()
            ) else Log.w("DEBUG", "No ip found")*/
          //  Log.d("TESTING NETWORK", "Start of try block")
            val queryProcessorSocket: Socket = Socket("192.168.1.12", port)
           // Log.d("TESTING NETWORK", "After socket")
            val queryProcessorDos =
                DataOutputStream(queryProcessorSocket.getOutputStream())
           // Log.d("TESTING NETWORK", "After stream called")
            //val queryProcessorReader =
                //BufferedReader(InputStreamReader(queryProcessorSocket.getInputStream()))
            queryProcessorDos.writeBytes( """ $data""".trimIndent())
           // Log.d("TESTING NETWORK", "Sent Data")
            //val response = queryProcessorReader.readLine()
            //Log.d("TESTING NETWORK", response)
        }
        catch (Error: Exception) { Error.printStackTrace()
        Log.e("ERROR WHEN TESTING", "Exception raised.")}
    }

}
