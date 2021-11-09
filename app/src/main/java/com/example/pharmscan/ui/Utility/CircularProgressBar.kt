package com.example.pharmscan.ui.Utility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CircularProgressBar(
    modifier: Modifier,
    color: Color, strokeWidth: Dp,
    isDisplayed: Boolean
){
    if (isDisplayed) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = modifier,
                color = color,
                strokeWidth = strokeWidth)
        }
    }
}