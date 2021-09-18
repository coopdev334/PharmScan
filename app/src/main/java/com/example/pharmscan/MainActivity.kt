package com.example.pharmscan

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.WindowManager
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
        val repo = PharmScanRepo(database.getHostCompNameDao(), database.getCollectedDataDao(), database.getSystemInfoDao(), database.getPSNdcDao(), database.getSettingsDao())
        val factory = PharmScanViewModelFactory(repo)
        val pharmScanViewModel = ViewModelProvider(this, factory).get(PharmScanViewModel::class.java)
// Sound Stuff
    val soundPool: SoundPool
    var sound1 : Int
    var sound2 : Int
    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()
    soundPool = SoundPool.Builder()
        .setMaxStreams(2)
        .setAudioAttributes(audioAttributes)
        .build()

    sound1 = soundPool.load(this,  com.example.pharmscan.R, 1)
    sound2 = soundPool.load(this, com.example.pharmscan.R, 1)

// */
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
