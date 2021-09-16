package com.example.pharmscan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Repository.PharmScanRepo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ViewModel.PharmScanViewModelFactory
import com.example.pharmscan.ui.Navigation.Navigate
import com.example.pharmscan.ui.Utility.ToastDisplay
import com.example.pharmscan.ui.theme.PharmScanTheme

class MainActivity() : ComponentActivity() {
    lateinit var ps: PharmScanViewModel
    //lateinit var receiver: PharmScanBroadcastReceiver

    // TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PharmScanDb.getDatabase(this)
        val repo = PharmScanRepo(
            database.getHostCompNameDao(),
            database.getCollectedDataDao(),
            database.getSystemInfoDao(),
            database.getPSNdcDao(),
            database.getSettingsDao()
        )
        val factory = PharmScanViewModelFactory(repo)
        ps = ViewModelProvider(this, factory).get(PharmScanViewModel::class.java)

        //receiver = PharmScanBroadcastReceiver(ps)
//        //Create broadcast receiver for scanner
//        val intentFilter = IntentFilter()
//        intentFilter.addAction("com.example.pharmscan.ACTION")
//        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
//        registerReceiver(PharmScanBroadcastReceiver(), intentFilter)

        setContent {
            PharmScanTheme {
                // A surface container using the 'background' color from the theme
                //Surface{
                Navigate(ps)
                //}
            }
        }
    }



//    override fun onResume() {
//        //Create broadcast receiver for scanner
//        val intentFilter = IntentFilter()
//        intentFilter.addAction("com.example.pharmscan.ACTION")
//        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
//        registerReceiver(receiver, intentFilter)
//        super.onResume()
//    }
//
//    override fun onPause() {
//        unregisterReceiver(receiver)
//        super.onPause()
//    }

//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        val decodedSource = intent.getStringExtra("com.symbol.datawedge.source");
//        val decodedData = intent.getStringExtra("com.symbol.datawedge.label_type");
//        val decodedLabelType = intent.getStringExtra("com.symbol.datawedge.data_string");
//        val output = "$decodedData | $decodedLabelType"
//        ToastDisplay(output, Toast.LENGTH_LONG)
//    }
}

class PharmScanBroadcastReceiver : BroadcastReceiver()  {

    //val psview = pharmScanViewModel

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getAction()
        val bundle = intent?.getExtras()
        ToastDisplay("onreceive", Toast.LENGTH_LONG)
        if (action == "com.example.pharmscan.ACTION") {
            // receive scan
            val decodedSource = intent.getStringExtra("com.symbol.datawedge.source");
            val decodedData = intent.getStringExtra("com.symbol.datawedge.label_type");
            val decodedLabelType = intent.getStringExtra("com.symbol.datawedge.data_string");
            val output = "$decodedData | $decodedLabelType"
            ToastDisplay(output, Toast.LENGTH_LONG)
            //psview.getAllCollectedData()
        }
    }

}
