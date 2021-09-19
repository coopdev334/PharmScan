package com.example.pharmscan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Data.Tables.PSNdc
import com.example.pharmscan.Repository.PharmScanRepo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ViewModel.PharmScanViewModelFactory
import com.example.pharmscan.ui.Navigation.Navigate
import com.example.pharmscan.ui.theme.PharmScanTheme
import java.io.File

class MainActivity() : ComponentActivity() {

    // TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PharmScanDb.getDatabase(this)
        val repo = PharmScanRepo(database.getHostCompNameDao(), database.getCollectedDataDao(), database.getSystemInfoDao(), database.getPSNdcDao(), database.getSettingsDao())
        val factory = PharmScanViewModelFactory(repo)
        val pharmScanViewModel = ViewModelProvider(this, factory).get(PharmScanViewModel::class.java)

        setContent {
            PharmScanTheme {
                // A surface container using the 'background' color from the theme
                //Surface{
                    Navigate(pharmScanViewModel)
                //}
            }
        }
    }
    readLines("/sdcard/Download/psndc.dat")
}
fun readLines(fileName: String) {
    checkPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE, 101)
    File(fileName).readBytes()
}

// Function to check and request permission.
fun checkPermission(permission: String, requestCode: Int) {
    Log.d("TESTING", "Checking the " + permission + " permissions.")
    if (ContextCompat.checkSelfPermission(
            this@MainActivity,
            permission
        ) == PackageManager.PERMISSION_DENIED
    ) {
        // Requesting the permission
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(permission),
            requestCode
        )
    } else {
        Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT)
            .show()
    }
}
override fun onRequestPermissionsResult(requestCode: Int,
                                        permissions: Array<String>,
                                        grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == 101) {

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this@MainActivity, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            Log.d("TESTING", "Inside permissions result.")
            File("/sdcard/Download/psndc.dat").readBytes()
        } else {
            Toast.makeText(this@MainActivity, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
}
}
}
//Create broadcast receiver for scanner
val intentFilter = IntentFilter()
intentFilter.addAction("com.example.pharmscan.ACTION")
intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
PharmScanApplication.context?.registerReceiver(receiver, intentFilter)

// Disable scanner when app starts
val intent = Intent()
intent.setAction("com.symbol.datawedge.api.ACTION")
intent.putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "DISABLE_PLUGIN")
sendBroadcast(intent)

readFileLineByLineUsingForEachLine(psViewModel, "/sdcard/Download/psndc.dat")
///sdcard/Download/psndc.dat
setContent {
    PharmScanTheme {
        // A surface container using the 'background' color from the theme
        //Surface{
        Navigate(psViewModel)
        //}
    }
}
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
        val bundle = intent?.getExtras()
        //ToastDisplay("onReceive", Toast.LENGTH_LONG)

        if (action == "com.example.pharmscan.ACTION") {
            // receive Datawedge scan
            val decodedSource = intent.getStringExtra("com.symbol.datawedge.source")
            val decodedData = intent.getStringExtra("com.symbol.datawedge.data_string")
            val decodedLabelType = intent.getStringExtra("com.symbol.datawedge.label_type")
            val output = "$decodedData | $decodedLabelType"
            //ToastDisplay(output, Toast.LENGTH_LONG)
            // Update Livedata in viewModel. Scan screen will then recompose and handle
            // processing the scan data looking for a match
            psViewModel.scanLiveData.value = ScanLiveData(decodedData, decodedLabelType)

        }
    }

}

fun readFileLineByLineUsingForEachLine(pharmScanViewModel: PharmScanViewModel, fileName: String){
    var psndc = PSNdc("","","")
    File(fileName).forEachLine {
        psndc.ndc = it.substring(0..10)
        psndc.price = it.substring(11..18)
        psndc.packsz = it.substring(19..26)
        pharmScanViewModel.insertPSNdc(psndc)
    }

}
