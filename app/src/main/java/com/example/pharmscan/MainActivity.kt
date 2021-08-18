package com.example.pharmscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pharmscan.ui.Navigation.Navigate
import com.example.pharmscan.ui.theme.PharmScanTheme
import com.example.pharmscan.ui.theme.Purple200

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
