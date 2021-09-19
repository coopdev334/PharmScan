package com.example.pharmscan

import android.app.DownloadManager
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pharmscan.Data.PharmScanDb
import com.example.pharmscan.Repository.PharmScanRepo
import com.example.pharmscan.ViewModel.PharmScanViewModel
import com.example.pharmscan.ViewModel.PharmScanViewModelFactory
import com.example.pharmscan.ui.Navigation.Navigate
import com.example.pharmscan.ui.theme.PharmScanTheme
import java.io.File
import java.util.jar.Manifest
import android.app.Activity





class MainActivity() : ComponentActivity() {

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
        val pharmScanViewModel =
            ViewModelProvider(this, factory).get(PharmScanViewModel::class.java)

        setContent {
            PharmScanTheme {
                // A surface container using the 'background' color from the theme
                //Surface{
                Navigate(pharmScanViewModel)
                //}
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

