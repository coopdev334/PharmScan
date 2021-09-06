package com.example.pharmscan

import android.os.Bundle
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
import com.example.pharmscan.ui.theme.PharmScanTheme

class MainActivity() : ComponentActivity() {

    // TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PharmScanDb.getDatabase(this)
        val repo = PharmScanRepo(database.getHostCompNameDao(), database.getCollectedDataDao(), database.getSystemInfoDao())
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
}
