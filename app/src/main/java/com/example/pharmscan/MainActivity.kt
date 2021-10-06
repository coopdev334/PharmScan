package com.example.pharmscan

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Data.ScanLiveData
import com.example.pharmscan.Repository.PharmScanRepo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ViewModel.PharmScanViewModelFactory
import com.example.pharmscan.ui.Navigation.Navigate
import com.example.pharmscan.ui.Utility.UpdateSystemInfo
import com.example.pharmscan.ui.Utility.writeToFile
import com.example.pharmscan.ui.theme.PharmScanTheme
import java.util.*

class MainActivity() : ComponentActivity() {
    private lateinit var psViewModel: PharmScanViewModel
    private lateinit var receiver: PharmScanBroadcastReceiver
    private lateinit var receiverNoNet: SystemMsgBroadcastReceiver

    // TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
    @ExperimentalAnimationGraphicsApi
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PharmScanDb.getDatabase(this)
        val repo = PharmScanRepo(
            database.getHostIpAddressDao(),
            database.getCollectedDataDao(),
            database.getSystemInfoDao(),
            database.getPSNdcDao(),
            database.getSettingsDao()
        )
        val factory = PharmScanViewModelFactory(repo)
        psViewModel = ViewModelProvider(this, factory).get(PharmScanViewModel::class.java)

        //Create broadcast receiver for scanner
        receiver = PharmScanBroadcastReceiver(psViewModel)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.pharmscan.ACTION")
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        PharmScanApplication.context?.registerReceiver(receiver, intentFilter)

        //Create broadcast receiver for no network warning screen
        receiverNoNet = SystemMsgBroadcastReceiver(this, psViewModel)
        val intentFilter1 = IntentFilter()
        intentFilter1.addAction("com.example.pharmscan.SYSTEM_MSG")
        intentFilter1.addCategory(Intent.CATEGORY_DEFAULT)
        PharmScanApplication.context?.registerReceiver(receiverNoNet, intentFilter1)

        // Disable scanner when app starts
        val intent = Intent()
        intent.setAction("com.symbol.datawedge.api.ACTION")
        intent.putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
        sendBroadcast(intent)

        //Log.d("TESTING", "Checking the " + Manifest.permission.READ_EXTERNAL_STORAGE + " permissions.")
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                101
            )
        }

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                101
            )
        }

        // Intialize database settings
        val sysInfoMap = mapOf("NdcLoading" to "off")
        UpdateSystemInfo(psViewModel, sysInfoMap)

        setContent {
            PharmScanTheme {
                // A surface container using the 'background' color from the theme
                //Surface{
                Navigate(psViewModel)
                //}
            }
        }
        val time: Date = Calendar.getInstance().getTime()
        writeToFile("Started the application on " + time.toString(), PharmScanApplication.context)
    }

    override fun onResume() {
        // Disable scanner when app starts
        val intent = Intent()
        intent.setAction("com.symbol.datawedge.api.ACTION")
        intent.putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
        sendBroadcast(intent)
        super.onResume()
    }

//        override fun onRestart() {
//        // Disable scanner when app starts
//        val intent = Intent()
//        intent.setAction("com.symbol.datawedge.api.ACTION")
//        intent.putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
//        sendBroadcast(intent)
//        super.onRestart()
//    }

        override fun onPause() {
            // Disable scanner when app starts
            val intent = Intent()
            intent.setAction("com.symbol.datawedge.api.ACTION")
            intent.putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
            sendBroadcast(intent)
        super.onPause()
    }


    // The code below is for a different method of working with broadcast receivers
    // This method registers and unregisters the receiver instead of using
    // application context. The application context keeps receiver active for entire
    // duration of app as opposed to below method. Currently not using this method
//  override fun onResume() {
//        val intentFilter = IntentFilter()
//        intentFilter.addAction("com.example.pharmscan.ACTION")
//        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
//        PharmScanApplication.context?.registerReceiver(receiver, intentFilter)
//        super.onResume()
//    }
//
//    override fun onPause() {
//        PharmScanApplication.context?.unregisterReceiver(receiver)
//        super.onPause()
//    }

    // This function override is used when using a startActivity intent instead of a
    // broadcast intent. When startActivity is intent is received it restarts activity
    // and calls this function. Currently not using this method
//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        val decodedSource = intent.getStringExtra("com.symbol.datawedge.source");
//        val decodedData = intent.getStringExtra("com.symbol.datawedge.label_type");
//        val decodedLabelType = intent.getStringExtra("com.symbol.datawedge.data_string");
//        val output = "$decodedData | $decodedLabelType"
//        ToastDisplay(output, Toast.LENGTH_LONG)
//    }
}

// This handles a broadcast intent sent from Datawedge scanner. It will receive barcode data and
// symbology type. It will update LiveData in viewmodel so the scan screen can react to the scan
// being received and recompose scan screen
class PharmScanBroadcastReceiver(pharmScanViewModel: PharmScanViewModel) : BroadcastReceiver()  {

    val psViewModel = pharmScanViewModel

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getAction()
        //ToastDisplay("onReceive", Toast.LENGTH_LONG)

        if (action == "com.example.pharmscan.ACTION") {
            // receive Datawedge scan
            //val decodedSource = intent.getStringExtra("com.symbol.datawedge.source")
            val decodedData = intent.getStringExtra("com.symbol.datawedge.data_string")
            val decodedLabelType = intent.getStringExtra("com.symbol.datawedge.label_type")
            //val output = "$decodedData | $decodedLabelType"
            //ToastDisplay(output, Toast.LENGTH_LONG)
            // Update Livedata in viewModel. Scan screen will then recompose and handle
            // processing the scan data looking for a match
            psViewModel.scanLiveData.value = ScanLiveData(decodedData, decodedLabelType)

        }
    }

}


class SystemMsgBroadcastReceiver(context: Context?, pharmScanViewModel: PharmScanViewModel) : BroadcastReceiver()  {
    val psViewModel = pharmScanViewModel
    val mainActivityContext = context

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getStringExtra("com.example.pharmscan.SYSTEM_MSG_TYPE")
        val content = intent?.getStringExtra("com.example.pharmscan.SYSTEM_MSG_CONTENT")
        val i = Intent(mainActivityContext, SystemMsgActivity::class.java)
        i.action = action

        if (action == "NONETWORK") {
            val sysInfo = psViewModel.getSystemInfoRow()
            val setting = psViewModel.getSettingsRow()
            i.putExtra("com.example.pharmscan.SYSTEM_MSG_HOSTIP", sysInfo[0].hostIpAddress)
            i.putExtra("com.example.pharmscan.SYSTEM_MSG_HOSTPORT", setting[0].hostServerPort)
        }

        i.putExtra("com.example.pharmscan.SYSTEM_MSG_CONTENT", content)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        Log.d("coop", "calling startActivity")
        mainActivityContext?.startActivity(i)
    }
}
