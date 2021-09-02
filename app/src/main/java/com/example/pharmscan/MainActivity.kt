package com.example.pharmscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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

    //private val pharmScanViewModel by viewModels<PharmScanViewModel>()
//    private val pharmScanViewModel: PharmScanViewModel by viewModels<PharmScanViewModel> {
//        PharmScanViewModelFactory((application as PharmScanApplication).repository)
//    }



    // TODO: @ExperimentalFoundationApi just for Text(.combinedClickable) may go away
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val pharmScanViewModel = ViewModelProvider(this).get(PharmScanViewModel::class.java)
        val database = PharmScanDb.getDatabase(this)
        val repo = PharmScanRepo(database.getHostCompNameDao())
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
