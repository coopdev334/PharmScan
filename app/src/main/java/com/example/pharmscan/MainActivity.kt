package com.example.pharmscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pharmscan.ui.Navigation.Navigate
import com.example.pharmscan.ui.theme.PharmScanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PharmScanTheme {
                // A surface container using the 'background' color from the theme
                //Surface{
                    Navigate()
                //}
            }
        }
    }
}
